//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_BACKEND_RECEIVER_H
#define PINQUIRY_BACKEND_RECEIVER_H

#include <string>
#define CPPHTTPLIB_USE_POLL
#define CPPHTTPLIB_OPENSSL_SUPPORT
#include "include/httplib.h"
#include "monitor_object.h"
#include <thread>

class BackendReceiver{
    httplib::Server svr;
    std::string backend_ip;
    int backend_port = 0;

    const std::function< ErrorString(const MonitorObject&)> add_monitor_handler;
    const std::function< ErrorString(const std::string&)> remove_monitor_handler;
    const std::function< ErrorString(const MonitorObject&)> update_monitor_handler;

    [[noreturn]] void receiver_worker();
public:
    BackendReceiver(std::string ip,
                    const std::function<ErrorString(const MonitorObject&)>& add_moni_handler,
                    const std::function<ErrorString(const std::string&)>& remove_mon_handler,
                    const std::function<ErrorString(const MonitorObject&)>& update_mon_handle);
    void start_listen();
};

#endif //PINQUIRY_BACKEND_RECEIVER_H
