//
// Created by ataya on 18-May-22.
//

#include "monitor_watcher.h"

MonitorWatcher::MonitorWatcher(){

}

void MonitorWatcher::add_monitors_begin(const std::vector<MonitorObject>& mons){
    for(auto const& mon : mons){
        monitors.insert(mon);
    }
}