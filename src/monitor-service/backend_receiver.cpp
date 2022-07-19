//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"
#include "monitor_watcher.h"

BackendReceiver::BackendReceiver(std::string ip, int port) : backend_ip(std::move(ip)), backend_port(port){

}

void BackendReceiver::receiver_thread(){
    httplib::Server svr;

    svr.Post( "/add_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });

    svr.Post( "/delete_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });

    svr.Post( "/update_monitor", [&](const httplib::Request& req, httplib::Response& res) {

    });
}

void BackendReceiver::start_listen(){

}