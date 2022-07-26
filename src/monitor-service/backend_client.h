//
// Created by ataya on 19-May-22.
//

#ifndef PINQUIRY_BACKEND_CLIENT_H
#define PINQUIRY_BACKEND_CLIENT_H

#include <vector>

#include "http_client.h"
#include "monitor_object.h"
#include "monitor_result.h"

class BackendClient{
    HttpClient backend_cli;
public:

    BackendClient(const std::string& ip, int port);
    void report_results(MonitorObject& mon_obj, MonitorResult& mon_res);
    std::vector<MonitorObject> get_monitors();
};

#endif //PINQUIRY_BACKEND_CLIENT_H
