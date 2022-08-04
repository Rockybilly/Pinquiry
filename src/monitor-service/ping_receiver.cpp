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
    th = std::thread(&PingReceiver::receiver_worker, this);
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
        long bytes = 0;
        addr_len = sizeof(r_addr);
        memset(&recv_pkt, 0, sizeof(recv_pkt));
        if ( recvfrom(sockfd, &recv_pkt,sizeof(recv_pkt),0,(sockaddr*)&r_addr,&addr_len) <= 0){
            std::cout << "\nPacket receive failed: " << strerror(errno) << std::endl;
        }
        else{
            uint64_t recvtime = get_epoch_ms();
            uint16_t id = recv_pkt.icmp_header.un.echo.id;
            uint16_t seq = htons(recv_pkt.icmp_header.un.echo.sequence);

            std::cout << "id: " << id << " seq: " << seq << std::endl;
            /*
            for (auto it = id_list_map.begin(); it != id_list_map.end(); ) {
                std::cout << "Key: " << it->first << std::endl;
            }*/

            list_map_mutex.lock_shared();

            if(!id_list_map.contains(id)){
                std::cout << "id not present in id_list_map." << std::endl;
                list_map_mutex.unlock_shared();
                continue;
            }

            id_list_map[id].first.lock();

            for (auto it = id_list_map[id].second.begin(); it != id_list_map[id].second.end(); ) {
                if(recvtime - it->timestamp > it->timeout_s * 1000){

                    auto* result = new PingResult();

                    result->timestamp_ms = it->timestamp;
                    result->response_time_ms = 0;
                    result->success = false;
                    result->mon_id = it->mon_id;
                    result->mon_type = MonitorObject::Type::PING;
                    result->error_str = "Ping timed out ( " + std::to_string(it->timeout_s) + " s ).";
                    report_result(result);

                    it = id_list_map[id].second.erase(it);
                }
                else if (it->sequence == seq){
                    auto* result = new PingResult();

                    result->timestamp_ms = it->timestamp;
                    result->response_time_ms = recvtime - it->timestamp;
                    result->success = true;
                    result->mon_id = it->mon_id;
                    result->mon_type = MonitorObject::Type::PING;

                    report_result(result);

                    it = id_list_map[id].second.erase(it);
                }
                else{
                    ++it;
                }



            }

            id_list_map[id].first.unlock();

            list_map_mutex.unlock_shared();


        }
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

    if(th.joinable()){
        th.join();
    }
}

