//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_MONITOR_OBJECT_H
#define PINQUIRY_MONITOR_OBJECT_H

#include <string>
#include <vector>

struct MonitorConnInfo {
    std::string protocol_server;
    std::string uri;
    int monitor_port = 0;
};

class MonitorObject{

    MonitorConnInfo mon;

    // For CONTENT type monitor;
    std::vector<MonitorConnInfo> mons;

public:
    typedef enum {
        HTTP,
        TCP, // Ping
        CONTENT
    } Type;

    MonitorObject(){

    }
};

#endif //PINQUIRY_MONITOR_OBJECT_H
