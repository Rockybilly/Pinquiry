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
#include <queue>
#include <semaphore>

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
    std::thread recv_th;
    std::thread proc_th;
    std::thread timeo_th;
    std::atomic<bool> stop_flag = false;
    std::function<void(MonitorResult*)> report_result;

    std::queue<std::pair<uint64_t, icmphdr>> process_queue;
    std::mutex queue_mutex;
    std::counting_semaphore<256> process_sem{0};
    void receiver_worker();
    void processor_worker();
    void timeout_worker();
public:
    struct IPPkt {
        iphdr ip_header;
        icmphdr icmp_header;
    };

    explicit PingReceiver(std::function<void(MonitorResult*)>&& report_result_handler);
    bool open_socket();
    int get_socket() const { return sockfd;};
    void add_new_id(uint16_t id);
    void remove_id(uint16_t id);
    void add_new_entry(uint16_t id, const PingClientEntry& entry);

    void stop();
};

#endif //PINQUIRY_PING_RECEIVER_H
