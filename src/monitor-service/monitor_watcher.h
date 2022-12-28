//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_MONITOR_WATCHER_H
#define PINQUIRY_MONITOR_WATCHER_H

#include <unordered_map>
#include <set>
#include "monitor_object.h"
#include "monitor_result.h"
#include <utility>
#include <vector>
#include <mutex>
#include <atomic>
#include <thread>
#include <functional>
#include "http_client.h"
#include "ping_client.h"
#include "ping_receiver.h"

struct WatcherWorker{

    std::atomic<bool> stop = false;
    std::atomic<bool> stopped = false;
    std::thread th;
    const MonitorObject mon;
    std::function<void(MonitorResult*)> report_result;
    explicit WatcherWorker(MonitorObject&& mon_obj, std::function<void(MonitorResult*)>&& report_result_handler) : mon(mon_obj), report_result(report_result_handler) {};
    virtual void watch() final{
        th = std::thread([this](){this->do_watch();});
    };
    virtual void do_watch() = 0;
    virtual ~WatcherWorker() = default;
};

struct PingWorker : WatcherWorker{
    int socket = 0;
    uint16_t id = 0;
    std::function<void(uint16_t, const PingReceiver::PingClientEntry&)> report_entry;
    PingWorker(MonitorObject mon_obj, std::function<void(MonitorResult*)> report_result_handler,
               std::function<void(uint16_t, const PingReceiver::PingClientEntry&)> report_entry_handler,
            uint16_t id, int socket) :
    WatcherWorker(std::move(mon_obj), std::move(report_result_handler)),
    report_entry(std::move(report_entry_handler)), id(id), socket(socket){};

    void do_watch() override;
};

struct HttpWorker : WatcherWorker{
    HttpWorker(MonitorObject mon_obj, std::function<void(MonitorResult*)> report_result_handler) :
    WatcherWorker(std::move(mon_obj), std::move(report_result_handler)){};
    void do_watch() override;
};

struct ContentWorker : WatcherWorker{
    ContentWorker(MonitorObject mon_obj, std::function<void(MonitorResult*)> report_result_handler) :
    WatcherWorker(std::move(mon_obj), std::move(report_result_handler)){};
    void do_watch() override;
};

class MonitorWatcher{

    std::set<WatcherWorker*> delete_wt_set;
    std::unordered_map<std::string, WatcherWorker*> watcher_threads;
    std::vector<MonitorResult*> results;
    std::mutex results_mutex;
    std::mutex watches_map_mutex;

    uint16_t ping_next_id = 0;
    PingReceiver ping_receiver;

    __pid_t pid = getpid();

    void add_result(MonitorResult* res);
public:
    MonitorWatcher() : ping_receiver([this](MonitorResult* res){add_result(res);}){}
    void add_monitors_begin(const std::vector<MonitorObject>& mons);
    void begin_watch();
    ErrorString add_monitor(const MonitorObject& mon, bool lock = true);
    ErrorString remove_monitor(const std::string& mon_id, bool lock = true);
    ErrorString update_monitor(const MonitorObject& mon);

    std::vector<MonitorResult*> get_results();

};

#endif //PINQUIRY_MONITOR_WATCHER_H
