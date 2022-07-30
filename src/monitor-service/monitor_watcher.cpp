//
// Created by ataya on 18-May-22.
//

#include "monitor_watcher.h"

static uint64_t get_epoch_ms(){
    return duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

/// Watcher Thread

WatcherThread::WatcherThread(MonitorObject mon_obj, std::function<void(MonitorResult*)> report_result_handler) : mon(std::move(mon_obj)), report_result(std::move(report_result_handler)){

}

void WatcherThread::watch_ping(){
    PingClient pc(mon.moncon.server, mon.moncon.timeout_s);

    while (!stop){
        uint64_t time_begin = get_epoch_ms();

        auto res = pc.send_ping();

        std::cout << "Ping " << mon.moncon.server << " total_time: " << res.response_time_ms << " ms." << std::endl;
        //std::cout << "Error string: " << res.error << std::endl;
        uint64_t time_end = get_epoch_ms();
        std::this_thread::sleep_for(std::chrono::milliseconds(mon.moncon.interval_s * 1000 - (time_end - time_begin)));
    }

    stopped = true;
}

void WatcherThread::watch_http(){
    HttpClient cli(mon.moncon.protocol + "://" + mon.moncon.server, mon.moncon.timeout_s * 1000);

    while (!stop){
        uint64_t time_begin = get_epoch_ms();
        HttpClient::Response res;

        if (mon.search_string.empty()){
            res = cli.head(mon.moncon.uri, mon.moncon.request_headers);
        }
        else{
            res = cli.get(mon.moncon.uri, mon.moncon.request_headers);
        }


        auto* result = new HTTPResult();

        result->response_time_ms = res.response_time_ms;
        result->status_code = res.status_code;
        result->timestamp_ms = res.timestamp_ms;
        result->mon_id = mon.mon_id;
        result->mon_type = mon.mon_type;

        if (mon.success_codes.contains(res.status_code)){
            result->status_code_success = true;
        }
        else{
            result->status_code_success = false;
            if(res.status_code == 0){
                result->error_str += "Error: " + res.error_message;
            }
            else if(res.status_code != 0){
                result->error_str += res.error_message;
            }
        }

        result->response_header_success = true;

        for(auto const& h : mon.response_success_headers){
            if (std::find(res.response_headers.begin(), res.response_headers.end(), h) == res.response_headers.end()){
                result->response_header_success = false;
                result->error_str += "Response header: " + h.first + ", value: " + h.second + " not found\n";
            }
        }

        if (mon.search_string.empty()){
            result->search_string_success = true;
        }
        else{
            if(res.body.find(mon.search_string) == std::string::npos){
                result->search_string_success = false;
                result->error_str += "Search string not found in response body\n";
            }
            else{
                result->search_string_success = true;
            }
        }

        report_result(static_cast<MonitorResult*>(result));

        uint64_t time_end = get_epoch_ms();
        std::this_thread::sleep_for(std::chrono::milliseconds(mon.moncon.interval_s * 1000 - (time_end - time_begin)));
    }

    stopped = true;
}

void WatcherThread::watch_content(){
    while (!stop){
        uint64_t time_begin = get_epoch_ms();
    }
    stopped = true;
}

void WatcherThread::watch(){
    if(mon.mon_type == MonitorObject::Type::PING){
        th = std::thread(&WatcherThread::watch_ping, this);
    }
    else if(mon.mon_type == MonitorObject::Type::HTTP){
        th = std::thread(&WatcherThread::watch_http, this);
    }
    else if(mon.mon_type == MonitorObject::Type::CONTENT){
        th = std::thread(&WatcherThread::watch_content, this);
    }
}

/// Monitor Watcher

MonitorWatcher::MonitorWatcher() = default;

void MonitorWatcher::add_monitors_begin(const std::vector<MonitorObject>& mons){
    for(auto const& mon : mons){
        auto* wt_ptr = new WatcherThread(mon, [this](MonitorResult* res){add_result(res);});
        watcher_threads.emplace(mon.mon_id, wt_ptr);
    }
}

void MonitorWatcher::begin_watch(){
    for(const auto& [id, watch] : watcher_threads){
        watch->watch();
    }
}

void MonitorWatcher::add_monitor(const MonitorObject& mon){
    watches_map_mutex.lock();

    auto* wt_ptr = new WatcherThread(mon, [this](MonitorResult* res){add_result(res);});
    watcher_threads.emplace(mon.mon_id, wt_ptr);

    watches_map_mutex.unlock();
}

void MonitorWatcher::remove_monitor(const std::string& mon_id){
    watches_map_mutex.lock();

    auto got = watcher_threads.find(mon_id);
    if( got != watcher_threads.end() ){
        got->second->stop = true;
    }

    delete_wt_set.insert(got->second);
    watcher_threads.erase(got);

    auto it = delete_wt_set.begin();
    for (it; it != delete_wt_set.end(); ) {
        if ((*it)->stopped){
            if((*it)->th.joinable()){
                (*it)->th.join();
            }
            auto store_ptr = (*it);
            it = delete_wt_set.erase(it);
            delete store_ptr;

        }
        else {
            ++it;
        }
    }

    watches_map_mutex.unlock();
}

void MonitorWatcher::update_monitor(const MonitorObject &mon){
    remove_monitor(mon.mon_id);
    add_monitor(mon);
}

void MonitorWatcher::add_result(MonitorResult* res){
    results_mutex.lock();
    results.emplace_back(res);
    results_mutex.unlock();
}

std::vector<MonitorResult*> MonitorWatcher::get_results(){
    results_mutex.lock();
    auto values = std::move(results);
    results.clear();
    results_mutex.unlock();

    return values;
}

