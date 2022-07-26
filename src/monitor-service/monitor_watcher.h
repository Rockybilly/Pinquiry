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

uint64_t get_epoch_ms(){
    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

struct WatcherThread{

    std::atomic<bool> stop = false;
    std::atomic<bool> stopped = false;
    std::thread th;
    std::function<void(MonitorResult&)> report_result;
    const MonitorObject mon;

    explicit WatcherThread(MonitorObject mon_obj, std::function<void(MonitorResult&)> report_result_handler) : mon(std::move(mon_obj)), report_result(std::move(report_result_handler)){

    }

    void watch_ping(){
        while (!stop){
            uint64_t time_begin = get_epoch_ms();

        }

        stopped = true;
    }

    void watch_http(){
        while (!stop){
            uint64_t time_begin = get_epoch_ms();

        }

        stopped = true;
    }

    void watch_content(){
        while (!stop){
            uint64_t time_begin = get_epoch_ms();

        }
        stopped = true;
    }

    void watch(){
        if(mon.mon_type == MonitorObject::Type::TCP){
            th = std::thread(&WatcherThread::watch_ping, this);
        }
        else if(mon.mon_type == MonitorObject::Type::HTTP){
            th = std::thread(&WatcherThread::watch_http, this);
        }
        else if(mon.mon_type == MonitorObject::Type::CONTENT){
            th = std::thread(&WatcherThread::watch_content, this);
        }
    }

};

class MonitorWatcher{

    std::set<WatcherThread*> delete_wt_set;
    std::unordered_map<std::string, WatcherThread*> watcher_threads;
    std::vector<MonitorResult> results;
    std::mutex results_mutex;
    std::mutex watches_map_mutex;
    void add_result(const MonitorResult& res);
public:
    MonitorWatcher();
    void add_monitors_begin(const std::vector<MonitorObject>& mons);
    void begin_watch();
    void add_monitor(const MonitorObject& mon);
    void remove_monitor(const std::string& mon_id);
    void update_monitor(const MonitorObject& mon);

    std::vector<MonitorResult> get_results();

};

#endif //PINQUIRY_MONITOR_WATCHER_H
