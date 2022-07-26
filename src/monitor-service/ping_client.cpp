//
// Created by ataya on 26-Jul-22.
//

#include "ping_client.h"

static uint64_t get_epoch_ms(){
    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

PingClient::PingClient(std::string in_server, uint8_t in_ping_recv_timeout_s) : recv_timeout_s(in_ping_recv_timeout_s),
                                                                                server(std::move(in_server)){
    uint16_t message_size = ping_pkt_size - sizeof(icmphdr);

    memcpy(pkt.message, const_msg.c_str(), message_size - 1);
    pkt.message[message_size - 1] = '\0';

    pkt.header.type = ICMP_ECHO;
    pkt.header.un.echo.id = getpid();
    pkt.header.un.echo.sequence = 0;
    pkt.header.checksum = checksum( &pkt );

    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr(server.c_str());

    int retry_count = 3;
    while (retry_count > 3 && !open_socket()){
        retry_count--;
    }

}

PingClient::PingResult PingClient::send_ping(){

    uint64_t start_time = get_epoch_ms();

    if (sendto(sockfd, &pkt, sizeof(pkt), 0, (sockaddr*) &addr, sizeof(addr)) <= 0){
        return {0, "Unable to send to socket."};
    }

    if (recvfrom(sockfd, &recv_pkt, sizeof(recv_pkt), 0, (sockaddr*)&r_addr, &addr_len) <= 0){
        return {0, "Unable to receive from socket."};
    }
    else{
        uint64_t end_time = get_epoch_ms();
        if ( !(recv_pkt.header.type == 69 && recv_pkt.header.code == 0) ){
            return {end_time - start_time, "Received packet with type: " + std::to_string(recv_pkt.header.type ) +
            ", code: " + std::to_string(recv_pkt.header.code)};
        }
        else{
            return {end_time - start_time, ""};
        }
    }
}

bool PingClient::open_socket(){
    uint32_t ttl_val = 64;
    sockfd = socket(PF_INET, SOCK_RAW, IPPROTO_ICMP);

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