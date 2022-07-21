//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"

BackendReceiver::BackendReceiver(std::string ip,
                                 std::function<std::pair<bool, std::string>(MonitorObject)> add_monitor_handler,
                                 std::function<std::pair<bool, std::string>(MonitorObject)> delete_monitor_handler,
                                 std::function<std::pair<bool, std::string>(MonitorObject)> update_monitor_handler)
                                 : backend_ip(std::move(ip)){

    svr.Post( "/add_monitor", [&](const httplib::Request& req, httplib::Response& res) {
        req.remote_addr
        add_monitor_handler();
    });

    svr.Post( "/delete_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });

    svr.Post( "/update_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });
}

void BackendReceiver::start_listen(){
    while (true){
        receiver_thread = std::thread(&httplib::Server::listen, std::ref(svr));
        receiver_thread.join();
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }


}