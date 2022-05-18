//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"

#include "monitor_watcher.h"

BackendConnector::BackendConnector(MonitorWatcher* watcher_pt, std::string ip, int port) : watcher(watcher_pt), backend_ip(std::move(ip)), backend_port(port){

}