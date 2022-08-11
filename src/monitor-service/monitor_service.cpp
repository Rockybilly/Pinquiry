//
// Created by ataya on 19-May-22.
//

#include "monitor_service.h"
#include "json_handler.h"


MonitorService::MonitorService(const std::string& ip, int port) :
    backend_receiver(ip, [this](const MonitorObject& mo) -> ErrorString { return add_monitor(mo);},
                     [this](const std::string& mon_id) -> ErrorString { return remove_monitor(mon_id);},
                     [this](const MonitorObject& mo) -> ErrorString { return update_monitor(mo);}),
                                       backend_client(ip, port){

}

void MonitorService::begin_service(){
    watcher.add_monitors_begin(backend_client.get_monitors());

    backend_receiver.start_listen();

    watcher.begin_watch();

    while (true){
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));

        auto results = watcher.get_results();
        if(!results.empty()){
            backend_client.report_results(json_create_multiple_results(results));
            std::cout << json_create_multiple_results(results) << std::endl;
        }



        for(auto* res : results){
            delete res;
        }
    }
}

ErrorString MonitorService::add_monitor(const MonitorObject& mo){
    return watcher.add_monitor(mo);
}

ErrorString MonitorService::remove_monitor(const std::string& mon_id){
    return watcher.remove_monitor(mon_id);
}

ErrorString MonitorService::update_monitor(const MonitorObject& mo){
    return watcher.update_monitor(mo);
}