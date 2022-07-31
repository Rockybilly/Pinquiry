//
// Created by ataya on 19-May-22.
//

#ifndef PINQUIRY_MONITOR_SERVICE_H
#define PINQUIRY_MONITOR_SERVICE_H

#include <string>
#include <unordered_set>

#include "monitor_watcher.h"
#include "backend_receiver.h"
#include "backend_client.h"

class MonitorService{
    MonitorWatcher watcher;
    BackendReceiver backend_receiver;
    BackendClient backend_client;

public:
    MonitorService(const std::string& ip, int port);
    [[noreturn]] void begin_service();
    ErrorString add_monitor(const MonitorObject& mo);
    ErrorString remove_monitor(const std::string& mon_id);
    ErrorString update_monitor(const MonitorObject& mo);
};

#endif //PINQUIRY_MONITOR_SERVICE_H
