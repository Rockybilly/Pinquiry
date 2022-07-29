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

constexpr uint8_t ping_pkt_size = 32;

class PingClient{
public:
    PingClient(std::string in_server, uint8_t in_ping_recv_timeout_s);

    struct PingResult{
        uint64_t response_time_ms;
        std::string error;
    };

    PingResult send_ping( );
private:
    const std::string server;
    const uint8_t recv_timeout_s;
    const std::string const_msg = "Pinquiry_test_message_p";
    int sockfd = 0;
    sockaddr_in addr{};
    sockaddr_in r_addr{};
    uint32_t addr_len = sizeof( r_addr );

    struct PingPkt {
        icmphdr header;
        char message[ping_pkt_size - sizeof(icmphdr)];
    };

    PingPkt pkt{};
    PingPkt recv_pkt{};

    bool open_socket();
    uint16_t checksum( void *buffer );

};

#endif //PINQUIRY_PING_CLIENT_H
