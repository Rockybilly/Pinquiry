//
// Created by ataya on 19-May-22.
//

#include "backend_client.h"

BackendClient::BackendClient(const std::string& ip, int port) : backend_cli(ip + ':' + std::to_string(port), 1, 8){

}

void BackendClient::report_single_result(MonitorObject& mon_obj, MonitorResult& mon_res){

}

std::vector<MonitorObject> BackendClient::get_monitors(){
    std::vector<MonitorObject> result;

    return result;
}