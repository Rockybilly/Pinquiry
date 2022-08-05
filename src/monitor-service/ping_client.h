//
// Created by ataya on 26-Jul-22.
//

#ifndef PINQUIRY_PING_CLIENT_H
#define PINQUIRY_PING_CLIENT_H

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netinet/ip_icmp.h>
#include <ctime>
#include <iostream>
#include <unistd.h>
#include <cstring>
#include <chrono>
#include <utility>

class PingClient{
public:
    PingClient(const std::string& server, uint16_t id, int sockfd);

    struct PingInfo{
        uint64_t timestamp;
        uint16_t sequence;
        std::string error_st;
    };

    PingInfo send_ping( );
private:
    int sockfd = 0;
    uint16_t sequence = 0;
    sockaddr_in addr{};

    struct PingPkt {
        icmphdr header;
    };

    PingPkt pkt{};
    uint16_t checksum( void *buffer );

};

#endif //PINQUIRY_PING_CLIENT_H
