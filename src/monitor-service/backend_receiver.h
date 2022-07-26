//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_BACKEND_RECEIVER_H
#define PINQUIRY_BACKEND_RECEIVER_H

#include <string>
#include "include/httplib.h"
#include "monitor_object.h"
#include <thread>

typedef std::pair<bool, std::string> OpResult;

class BackendReceiver{
    httplib::Server svr;
    std::string backend_ip;
    int backend_port = 0;
    [[noreturn]] void receiver_worker();
public:
    BackendReceiver(std::string ip,
                    const std::function<OpResult(const MonitorObject&)>& add_monitor_handler,
                    const std::function<OpResult(const MonitorObject&)>& remove_monitor_handler,
                    const std::function<OpResult(const MonitorObject&)>& update_monitor_handle);
    void start_listen();
};

#endif //PINQUIRY_BACKEND_RECEIVER_H
