//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_BACKEND_RECEIVER_H
#define PINQUIRY_BACKEND_RECEIVER_H


#include <string>
#include "include/httplib.h"

class MonitorWatcher;

class BackendConnector{
    std::string backend_ip;
    int backend_port = 0;
    MonitorWatcher* watcher = nullptr;
public:
    BackendConnector(MonitorWatcher* watcher_pt, std::string ip, int port);
};

#endif //PINQUIRY_BACKEND_RECEIVER_H
