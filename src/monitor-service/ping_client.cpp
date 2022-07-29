//
// Created by ataya on 26-Jul-22.
//

#include "ping_client.h"
#include <thread>
#include <mutex>
#include <syscall.h>

static pid_t my_gettid(){
    return syscall(SYS_gettid);
}

std::mutex global_mutex;
uint64_t get_epoch_ms(){

    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

typedef std::chrono::time_point<std::chrono::system_clock,std::chrono::nanoseconds> TimeObject;

PingClient::PingClient(std::string in_server, uint8_t in_ping_recv_timeout_s) : recv_timeout_s(in_ping_recv_timeout_s),
                                                                                server(std::move(in_server)){
    uint16_t message_size = ping_pkt_size - sizeof(icmphdr);

    memcpy(pkt.message, const_msg.c_str(), message_size - 1);
    pkt.message[message_size - 1] = '\0';

    pkt.header.type = ICMP_ECHO;
    pkt.header.un.echo.id = my_gettid();
    pkt.header.un.echo.sequence = 0;
    pkt.header.checksum = checksum( &pkt );

    addr.sin_family = AF_INET;
    inet_aton(server.c_str(), &addr.sin_addr);
    //addr.sin_addr.s_addr = inet_addr(server.c_str());

    char buf[256];
    global_mutex.lock();
    std::cout << server << " <-- server, parsed --> " << inet_ntop(AF_INET, &(addr.sin_addr), buf, INET_ADDRSTRLEN) << " " << pkt.header.un.echo.id << std::endl;
    global_mutex.unlock();

    int retry_count = 3;
    while (retry_count > 0 && !open_socket()){
        retry_count--;
        std::this_thread::sleep_for(std::chrono::milliseconds(500));
    }

}

PingClient::PingResult PingClient::send_ping(){
    global_mutex.lock();
    TimeObject start_time = std::chrono::high_resolution_clock::now();;

    addr_len = sizeof(r_addr);
    if (sendto(sockfd, &pkt, sizeof(pkt), 0, (sockaddr*) &addr, sizeof(addr)) <= 0){
        return {0, "Unable to send to socket: " + std::string(strerror(errno))};
    }

    if (recvfrom(sockfd, &recv_pkt, sizeof(recv_pkt), MSG_WAITALL, (sockaddr*)&r_addr, &addr_len) <= 0){
        return {0, "Unable to receive from socket."};
    }
    else{
        TimeObject end_time = std::chrono::high_resolution_clock::now();

        char buf[256];

        //std::cout << server << " <-- server, parsed --> " << inet_ntop(AF_INET, &(addr.sin_addr), buf, INET_ADDRSTRLEN) << std::endl;
        //std::cout << "received --> " << inet_ntop(AF_INET, &(r_addr.sin_addr), buf, INET_ADDRSTRLEN) << std::endl;

        if ( !(recv_pkt.header.type == 69 && recv_pkt.header.code == 0) ){
            return {static_cast<uint64_t>(std::chrono::duration_cast<std::chrono::nanoseconds>( end_time - start_time).count()), "Received packet with type: " + std::to_string(recv_pkt.header.type ) +
            ", code: " + std::to_string(recv_pkt.header.code)};
        }
        else{
            global_mutex.unlock();
            return {static_cast<uint64_t>(std::chrono::duration_cast<std::chrono::nanoseconds>( end_time - start_time).count()), ""};
        }
    }

}

bool PingClient::open_socket(){
    uint32_t ttl_val = 64;
    sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);

    if (sockfd == -1 ){
        return false;
    }

    if ( setsockopt(sockfd,
                    IPPROTO_IP,
                    IP_TTL, // only affects outgoing unicast datagrams
                    &ttl_val,
                    sizeof(ttl_val)) != 0 ){
        return false;
    }

    timeval tv_timeout{};
    tv_timeout.tv_sec = recv_timeout_s;
    tv_timeout.tv_usec = 0;

    setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, &tv_timeout, sizeof(tv_timeout));

    if ( setsockopt(sockfd,
                    IPPROTO_IP,
                    IP_TTL, // only affects outgoing unicast datagrams
                    &ttl_val,
                    sizeof(ttl_val)) != 0 ){
        return false;
    }

    return true;
}

uint16_t PingClient::checksum( void *buffer ) {
    auto *buff = (uint16_t*)buffer;
    uint32_t sum;
    uint16_t result;
    uint16_t length = sizeof(pkt);

    for ( sum = 0; length > 1; length -= 2 ){
        sum += *(buff++);
    }

    if ( length == 1 ){
        sum += *(unsigned char*)buff;
    }

    sum = (sum >> 16) + (sum & 0xFFFF);
    sum += (sum >> 16);
    result = ~sum;
    return result;
}