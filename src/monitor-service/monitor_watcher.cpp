//
// Created by ataya on 18-May-22.
//

#include "monitor_watcher.h"

MonitorWatcher::MonitorWatcher() = default;

void MonitorWatcher::add_monitors_begin(const std::vector<MonitorObject>& mons){
    for(auto const& mon : mons){
        auto* wt_ptr = new WatcherThread(mon, [this](const MonitorResult& res){add_result(res);});
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

    auto* wt_ptr = new WatcherThread(mon, [this](const MonitorResult& res){add_result(res);});
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

void MonitorWatcher::add_result(const MonitorResult& res){
    results_mutex.lock();
    results.emplace_back(res);
    results_mutex.unlock();
}

std::vector<MonitorResult> MonitorWatcher::get_results(){
    results_mutex.lock();
    auto values = std::move(results);
    results.clear();
    results_mutex.unlock();

    return values;
}
