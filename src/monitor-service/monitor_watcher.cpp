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
    HttpClient cli(mon.moncon.protocol + "://" + mon.moncon.server + ':' + std::to_string(mon.moncon.port), mon.moncon.timeout_s * 1000);

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
        result->server_ip = cli.get_ip();
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
                result->error_str += "HTTP " + std::to_string(res.status_code);
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

        if(!result->error_str.empty()){
            result->response_headers = std::move(res.response_headers);
        }

        report_result(static_cast<MonitorResult*>(result));

        uint64_t time_end = get_epoch_ms();
        std::this_thread::sleep_for(std::chrono::milliseconds(mon.moncon.interval_s * 1000 - (time_end - time_begin)));
    }

    stopped = true;
}

void WatcherThread::watch_content(){
    std::vector<HttpClient> clients;

    for(auto const& moncon : mon.moncons){

        clients.emplace_back(moncon.protocol + "://" + moncon.server + ':' + std::to_string(moncon.port), moncon.timeout_s * 1000);
    }

    while (!stop){
        uint64_t time_begin = get_epoch_ms();

        std::vector<HttpClient::Response> responses;

        for(int i = 0; i < clients.size(); i++){
            HttpClient::Response res;

            res = clients[i].get(mon.moncons[i].uri, mon.moncons[i].request_headers);
            responses.push_back(std::move(res));
        }

        auto* result = new ContentResult();

        result->mon_id = mon.mon_id;
        result->mon_type = mon.mon_type;

        std::vector<std::vector<std::string>> groups_bodies;
        std::vector<std::vector<ContentResult::SingleResult>> groups;

        for(int i = 0; i < responses.size(); i++){
            ContentResult::SingleResult sr{};
            auto& r = responses[i];

            sr.response_time_ms = r.response_time_ms;
            sr.status_code = r.status_code;
            sr.timestamp_ms = r.timestamp_ms;

            sr.response_headers = std::move(r.response_headers);
            sr.url = mon.moncons[i].protocol + "://" + mon.moncons[i].server +
                    ':' + std::to_string(mon.moncons[i].port) + mon.moncons[i].uri;
            sr.server_ip = clients[i].get_ip();
            sr.body_size = r.body.size();

            bool need_new_group = true;

            for(int j = 0; j < groups.size(); j++){
                if(r.body == groups_bodies[j][0]){
                    need_new_group = false;
                    groups_bodies[j].push_back(std::move(r.body));
                    groups[j].push_back(std::move(sr));
                    break;
                }
            }

            if (need_new_group){
                groups_bodies.push_back({std::move(r.body)});
                groups.push_back({std::move(sr)});
            }
        }

        result->num_of_groups = groups.size();
        result->groups = std::move(groups);

        report_result(static_cast<MonitorResult*>(result));

        uint64_t time_end = get_epoch_ms();
        std::this_thread::sleep_for(std::chrono::milliseconds(mon.moncon.interval_s * 1000 - (time_end - time_begin)));
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

ErrorString MonitorWatcher::add_monitor(const MonitorObject& mon){
    std::lock_guard<std::mutex> lock(watches_map_mutex);

    auto* wt_ptr = new WatcherThread(mon, [this](MonitorResult* res){add_result(res);});
    auto [_, inserted] = watcher_threads.emplace(mon.mon_id, wt_ptr);

    if (!inserted){
        delete wt_ptr;
        return "Couldn't insert existing mon_id";
    }

    wt_ptr->watch();
    return "";
}

ErrorString MonitorWatcher::remove_monitor(const std::string& mon_id){
    std::lock_guard<std::mutex> lock(watches_map_mutex);

    auto got = watcher_threads.find(mon_id);
    if( got != watcher_threads.end() ){
        got->second->stop = true;
    }
    else{
        return "mon_id not found.";
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

    return "";
}

ErrorString MonitorWatcher::update_monitor(const MonitorObject &mon){
    std::lock_guard<std::mutex> lock(watches_map_mutex);

    auto got = watcher_threads.find(mon.mon_id);
    if( got != watcher_threads.end() ){
        got->second->stop = true;
    }
    else{
        return "mon_id not found.";
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

    auto* wt_ptr = new WatcherThread(mon, [this](MonitorResult* res){add_result(res);});
    auto [_, inserted] = watcher_threads.emplace(mon.mon_id, wt_ptr);

    if (!inserted){
        delete wt_ptr;
        return "Couldn't insert existing mon_id (Shouldn't happen in update method, consult a system admin).";
    }

    wt_ptr->watch();

    return "";
}

void MonitorWatcher::add_result(MonitorResult* res){
    std::lock_guard<std::mutex> lock(results_mutex);
    results.emplace_back(res);
}

std::vector<MonitorResult*> MonitorWatcher::get_results(){
    std::lock_guard<std::mutex> lock(results_mutex);

    auto values = std::move(results);
    results.clear();

    return values;
}

