//
// Created by ataya on 03-Aug-22.
//

#ifndef PINQUIRY_PING_RECEIVER_H
#define PINQUIRY_PING_RECEIVER_H

#include <netinet/in.h>
#include <arpa/inet.h>
#include <netinet/ip_icmp.h>
#include <list>
#include <unordered_map>
#include "monitor_result.h"
#include <mutex>
#include <thread>
#include <functional>
#include <shared_mutex>

class PingReceiver{
public:
    struct PingClientEntry{
        std::string mon_id;
        uint64_t timestamp;
        uint16_t sequence;
        uint8_t timeout_s;
    };
private:
    using EntryList = std::pair<std::mutex, std::list<PingClientEntry>>;
    std::unordered_map<uint16_t, EntryList> id_list_map;
    std::shared_mutex list_map_mutex;
    int sockfd = -1;

    std::thread th;
    std::atomic<bool> stop_flag = false;
    std::function<void(MonitorResult*)> report_result;
    void receiver_worker();
public:
    explicit PingReceiver(std::function<void(MonitorResult*)>&& report_result_handler);

    sockaddr_in r_addr{};
    uint32_t addr_len = sizeof( r_addr );

    struct PingPkt {
        icmphdr header;
    };

    struct IPPkt {
        iphdr ip_header;
        icmphdr icmp_header;
    };

    IPPkt recv_pkt{};
    bool open_socket();
    int get_socket() const { return sockfd;};
    void add_new_id(uint16_t id);
    void remove_id(uint16_t id);
    void add_new_entry(uint16_t id, const PingClientEntry& entry);

    void stop();
};

#endif //PINQUIRY_PING_RECEIVER_H
