//
// Created by ataya on 26-Jul-22.
//

#include "ping_client.h"
#include <thread>
#include <mutex>
#include <syscall.h>

uint64_t get_epoch_ms(){
    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

PingClient::PingClient(const std::string& server, uint16_t id, int socket) : sockfd(socket){
    pkt.header.type = ICMP_ECHO;
    pkt.header.un.echo.id = htons(id);
    memcpy(pkt.data, "pinquiry", 8);

    addr.sin_family = AF_INET;
    inet_aton(server.c_str(), &addr.sin_addr);
}

PingClient::PingInfo PingClient::send_ping(){

    pkt.header.un.echo.sequence = htons(sequence++);
    pkt.header.checksum = 0;
    pkt.header.checksum = checksum( &pkt );
    std::cout << "ID: " << ntohs(pkt.header.un.echo.id ) << ", Sequence: " << sequence - 1 << std::endl;
    if (sendto(sockfd, &pkt, sizeof(pkt), 0, (sockaddr*) &addr, sizeof(addr)) <= 0){
        return {get_epoch_ms(), static_cast<uint16_t>(sequence - 1), "Unable to send to socket: " + std::string(strerror(errno))};
    }

    return {get_epoch_ms(), static_cast<uint16_t>(sequence - 1), ""};
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