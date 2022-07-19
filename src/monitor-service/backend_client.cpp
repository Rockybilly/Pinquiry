//
// Created by ataya on 19-May-22.
//

#include "backend_client.h"
#include "include/rapidjson/document.h"

BackendClient::BackendClient(const std::string& ip, int port) : backend_cli(ip + ':' + std::to_string(port), 1, 8){

}

void BackendClient::report_single_result(MonitorObject& mon_obj, MonitorResult& mon_res){

}

std::vector<MonitorObject> BackendClient::get_monitors(){
    std::vector<MonitorObject> result;
    int status_code = 0;

    while (status_code != 200){
        auto res = backend_cli.get("/get_monitors");
        status_code = res.status_code;
        if(status_code != 200){
            std::cerr << "Could not get monitors from the backend." << std::endl;
        }
        else if (!res.body.empty()){
            rapidjson::Document document;
            document.Parse(res.body.c_str());

        }
    }


    return result;
}