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
};

#endif //PINQUIRY_MONITOR_SERVICE_H
