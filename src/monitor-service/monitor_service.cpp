//
// Created by ataya on 19-May-22.
//

#include "monitor_service.h"
#include "json_handler.h"


MonitorService::MonitorService(const std::string& ip, int port) :
    backend_receiver(ip, [this](const MonitorObject& mo) -> OpResult { return add_monitor(mo);},
                     [this](const MonitorObject& mo) -> OpResult { return remove_monitor(mo);},
                     [this](const MonitorObject& mo) -> OpResult { return update_monitor(mo);}),
                                       backend_client(ip, port){

}

void MonitorService::begin_service(){
    watcher.add_monitors_begin(backend_client.get_monitors());
    watcher.begin_watch();

    while (true){
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));

        auto results = watcher.get_results();
        std::cout << json_create_multiple_results(results) << std::endl;

    }
}

std::pair<bool, std::string> MonitorService::add_monitor(const MonitorObject& mo){
    return {};
}

std::pair<bool, std::string> MonitorService::remove_monitor(const MonitorObject& mo){
    return {};
}

std::pair<bool, std::string> MonitorService::update_monitor(const MonitorObject& mo){
    return {};
}