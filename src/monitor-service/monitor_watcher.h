//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_MONITOR_WATCHER_H
#define PINQUIRY_MONITOR_WATCHER_H

#include <unordered_set>

#include "backend_receiver.h"
#include "monitor_object.h"
#include <vector>

class MonitorWatcher{

    std::unordered_set<MonitorObject, MonitorObject::Hash, MonitorObject::Equal> monitors;

public:
    MonitorWatcher();
    void add_monitors_begin(const std::vector<MonitorObject>& mons);
    void add_monitor(const MonitorObject& mon);
    void remove_monitor(const std::string& mon_id);
};

#endif //PINQUIRY_MONITOR_WATCHER_H
