//
// Created by ataya on 19-May-22.
//

#include "monitor_service.h"


MonitorService::MonitorService(const std::string& ip, int port) :
    backend_receiver(ip, [this](const MonitorObject& mo) -> OpResult { return add_monitor(mo);},
                     [this](const MonitorObject& mo) -> OpResult { return remove_monitor(mo);},
                     [this](const MonitorObject& mo) -> OpResult { return update_monitor(mo);}),
                                       backend_client(ip, port){

}

void MonitorService::begin_service(){
    watcher.add_monitors_begin(backend_client.get_monitors());
    watcher.begin_watch();
}

std::pair<bool, std::string> MonitorService::add_monitor(const MonitorObject& mo){

}

std::pair<bool, std::string> MonitorService::remove_monitor(const MonitorObject& mo){

}

std::pair<bool, std::string> MonitorService::update_monitor(const MonitorObject& mo){

}