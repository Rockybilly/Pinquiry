//
// Created by ataya on 03-Aug-22.
//

#include <cstring>
#include <iostream>
#include "ping_receiver.h"

static uint64_t get_epoch_ms(){
    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

PingReceiver::PingReceiver(std::function<void(MonitorResult*)>&& report_result_handler) : report_result(report_result_handler){
    open_socket();
    recv_th = std::thread(&PingReceiver::receiver_worker, this);
    proc_th = std::thread(&PingReceiver::processor_worker, this);
    timeo_th = std::thread(&PingReceiver::timeout_worker, this);
    std::cout << "Ping receiver started." << std::endl;
}

bool PingReceiver::open_socket(){
    sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);

    if (sockfd == -1){
        return false;
    }


    //if ( setsockopt(sockfd, SOL_RAW, 1, ~(1<<ICMP_ECHOREPLY|1<<ICMP_DEST_UNREACH|1<<ICMP_SOURCE_QUENCH|1<<ICMP_REDIRECT|1<<ICMP_TIME_EXCEEDED|1<<ICMP_PARAMETERPROB), 4) != 0){
        //return false;

    //}
    return true;
}

void PingReceiver::receiver_worker(){
    while (!stop_flag){
        sockaddr_in r_addr{};
        uint32_t addr_len = sizeof( r_addr );
        IPPkt recv_pkt{};

        if ( recvfrom(sockfd, &recv_pkt,sizeof(recv_pkt),0,(sockaddr*)&r_addr,&addr_len) <= 0){
            std::cout << "\nPacket receive failed: " << strerror(errno) << std::endl;
        }
        else{
            uint64_t recvtime = get_epoch_ms();
            //std::cout << "Received id: " << recv_pkt.icmp_header.un.echo.id << " seq: " << htons(recv_pkt.icmp_header.un.echo.sequence) << std::endl;

            queue_mutex.lock();
            process_queue.emplace(recvtime, recv_pkt.icmp_header);
            queue_mutex.unlock();

            process_sem.release();

        }
    }
}

void PingReceiver::processor_worker(){
    while (!stop_flag){
        process_sem.acquire();
        if(stop_flag) break;

        queue_mutex.lock();
        auto [recvtime, header]  = process_queue.front();
        process_queue.pop();
        queue_mutex.unlock();

        uint16_t id = htons(header.un.echo.id);
        uint16_t seq = htons(header.un.echo.sequence);

       // std::cout << "Processing id: " << id << " seq: " << seq << std::endl;

        list_map_mutex.lock_shared();

        if(!id_list_map.contains(id)){
            std::cout << "id not present in id_list_map: " << id << std::endl;
            list_map_mutex.unlock_shared();
            continue;
        }

        id_list_map[id].first.lock();

        for (auto it = id_list_map[id].second.begin(); it != id_list_map[id].second.end(); ) {
            if (it->sequence == seq){
                auto* result = new PingResult();

                result->timestamp_ms = it->timestamp;
                result->response_time_ms = recvtime - it->timestamp;
                result->success = true;
                result->mon_id = it->mon_id;
                result->mon_type = MonitorObject::Type::PING;

                report_result(result);

                id_list_map[id].second.erase(it);
                break;
            }
            else{
                ++it;
            }
        }

        id_list_map[id].first.unlock();

        list_map_mutex.unlock_shared();
        }
}

void PingReceiver::timeout_worker(){
    while (!stop_flag){

        list_map_mutex.lock_shared();

        auto timestamp = get_epoch_ms();

        for(auto& [id, el] :  id_list_map){
            std::cout << "Looking at id: " << id << std::endl;

            el.first.lock();

            for (auto it = el.second.begin(); it != el.second.end(); ) {
                if(timestamp - it->timestamp > it->timeout_s * 1000){

                    auto* result = new PingResult();

                    result->timestamp_ms = it->timestamp;
                    result->response_time_ms = 0;
                    result->success = false;
                    result->mon_id = it->mon_id;
                    result->mon_type = MonitorObject::Type::PING;
                    result->error_str = "Ping timed out (" + std::to_string(it->timeout_s) + "s).";
                    report_result(result);

                    it = el.second.erase(it);
                }
                else{
                    ++it;
                }
            }
            el.first.unlock();
        }

        list_map_mutex.unlock_shared();

        std::this_thread::sleep_for(std::chrono::seconds(2));
    }
}

void PingReceiver::add_new_id(uint16_t id){
    std::unique_lock<std::shared_mutex> lck(list_map_mutex);
    //id_list_map.emplace(id);

    id_list_map.emplace(std::piecewise_construct,
              std::forward_as_tuple(id),
              std::forward_as_tuple());
}

void PingReceiver::remove_id(uint16_t id){
    std::unique_lock<std::shared_mutex> lck(list_map_mutex);
    id_list_map.erase(id);
}

void PingReceiver::add_new_entry(uint16_t id, const PingReceiver::PingClientEntry& entry){
    std::shared_lock<std::shared_mutex> lck(list_map_mutex);

    if(!id_list_map.contains(id)){
        std::cout << "id not present in id_list_map." << std::endl;
        return;
    }

    std::lock_guard<std::mutex> lck2(id_list_map[id].first);
    id_list_map[id].second.push_back(entry);
}

void PingReceiver::stop(){
    stop_flag = true;
    shutdown(sockfd, SHUT_RDWR);

    if(recv_th.joinable()){
        recv_th.join();
    }

    if(proc_th.joinable()){
        process_sem.release();
        proc_th.join();
    }
}

