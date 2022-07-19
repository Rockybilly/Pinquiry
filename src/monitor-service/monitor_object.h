//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_MONITOR_OBJECT_H
#define PINQUIRY_MONITOR_OBJECT_H

#include <string>
#include <vector>
#include <map>

struct MonitorConnInfo {
    std::string protocol_server;
    std::string uri;
    int monitor_port = 0;
    std::map<std::string, std::string> request_headers;
};

class MonitorObject{

    std::string mon_id;
    MonitorConnInfo moncon;

    // For CONTENT type monitor;
    std::vector<MonitorConnInfo> mons;

    std::map<std::string, std::string> response_success_headers;
    std::vector<int> success_codes;
public:
    struct Hash { std::size_t operator()(const MonitorObject& mo) const { return std::hash<std::string>{}(mo.mon_id); } };
    struct Equal { bool operator()(const MonitorObject&mo1, const MonitorObject&mo2) const { return mo1.mon_id == mo2.mon_id; } };

    typedef enum {
        HTTP,
        TCP, // Ping
        CONTENT
    } Type;

    MonitorObject(){

    }
};


#endif //PINQUIRY_MONITOR_OBJECT_H