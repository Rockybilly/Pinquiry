//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"

#include "monitor_watcher.h"

BackendReceiver::BackendReceiver(std::string ip, int port) : backend_ip(std::move(ip)), backend_port(port){

}