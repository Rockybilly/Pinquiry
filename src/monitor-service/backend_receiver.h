//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_BACKEND_RECEIVER_H
#define PINQUIRY_BACKEND_RECEIVER_H

#include <string>
#include "include/httplib.h"
#include <thread>
#include "monitor_watcher.h"

class BackendReceiver{
    httplib::Server svr;
    std::string backend_ip;
    int backend_port = 0;
    std::thread receiver_thread;
public:
    BackendReceiver(std::string ip,
                    std::function<std::pair<bool, std::string>(MonitorObject)> add_monitor_handler,
                    std::function<std::pair<bool, std::string>(MonitorObject)> delete_monitor_handler,
                    std::function<std::pair<bool, std::string>(MonitorObject)> update_monitor_handle);
    void start_listen();
};

#endif //PINQUIRY_BACKEND_RECEIVER_H
