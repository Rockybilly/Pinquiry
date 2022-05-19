//
// Created by ataya on 19-May-22.
//

#include "monitor_service.h"

#include <utility>

MonitorService::MonitorService(const std::string& ip, int port) : backend_receiver(ip, port), backend_client(ip, port){
    watcher.add_monitors_begin(backend_client.get_monitors());
}