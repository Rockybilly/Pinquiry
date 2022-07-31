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

struct WatcherThread{

    std::atomic<bool> stop = false;
    std::atomic<bool> stopped = false;
    std::thread th;
    std::function<void(MonitorResult*)> report_result;
    const MonitorObject mon;

    explicit WatcherThread(MonitorObject mon_obj, std::function<void(MonitorResult*)> report_result_handler);
    void watch_ping();
    void watch_http();
    void watch_content();
    void watch();
};

class MonitorWatcher{

    std::set<WatcherThread*> delete_wt_set;
    std::unordered_map<std::string, WatcherThread*> watcher_threads;
    std::vector<MonitorResult*> results;
    std::mutex results_mutex;
    std::mutex watches_map_mutex;
    void add_result(MonitorResult* res);
public:
    MonitorWatcher();
    void add_monitors_begin(const std::vector<MonitorObject>& mons);
    void begin_watch();
    ErrorString add_monitor(const MonitorObject& mon);
    ErrorString remove_monitor(const std::string& mon_id);
    ErrorString update_monitor(const MonitorObject& mon);

    std::vector<MonitorResult*> get_results();

};

#endif //PINQUIRY_MONITOR_WATCHER_H
