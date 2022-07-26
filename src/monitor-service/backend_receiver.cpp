//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"

BackendReceiver::BackendReceiver(std::string ip,
                                 const std::function< OpResult(const MonitorObject&)>& add_monitor_handler,
                                 const std::function< OpResult(const MonitorObject&)>& remove_monitor_handler,
                                 const std::function< OpResult(const MonitorObject&)>& update_monitor_handler)
                                 : backend_ip(std::move(ip)){

    svr.Post( "/add_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });

    svr.Post( "/remove_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });

    svr.Post( "/update_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });
}

void BackendReceiver::receiver_worker(){
    while (true){
        svr.listen("0.0.0.0", 6363);
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void BackendReceiver::start_listen(){
    std::thread(&BackendReceiver::receiver_worker, this).detach();
}