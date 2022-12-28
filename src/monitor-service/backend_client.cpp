//
// Created by ataya on 19-May-22.
//

#include "backend_client.h"
#include "include/rapidjson/document.h"
#include "json_handler.h"

BackendClient::BackendClient(const std::string& ip, int port) : backend_cli("http://" + ip + ':' + std::to_string(port), 5000){
    std::cout << "Monitor, opening backend connection with: " << ip + ':' + std::to_string(port) << std::endl;
}


std::vector<MonitorObject> BackendClient::get_monitors(){
    std::vector<MonitorObject> result;
    int status_code = 0;

    while (status_code != 200){
        auto res = backend_cli.get("/get_monitors");
        status_code = res.status_code;

        if(status_code != 200){
            std::cerr << "Could not get monitors from the backend, " << "status_code: "
            << status_code << ", error:" << res.error_message << std::endl;
        }
        else if (res.body.empty()){
            std::cerr << "Backend get_monitors result shouldn't be empty." << std::endl;
        }
        else{
            auto [mon_vector, error_string] = json_parse_multiple_monitors(res.body);
            if (!error_string.empty()){
                std::cerr << "JSON ERROR: " << error_string << std::endl;
            }
            else{
                return mon_vector;
            }

        }

        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }

    return {};
}

void BackendClient::report_results(const std::string& result) {
    backend_cli.post_body("/add_results", {}, result);
}
