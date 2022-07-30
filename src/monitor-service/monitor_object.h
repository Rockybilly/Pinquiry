//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_MONITOR_OBJECT_H
#define PINQUIRY_MONITOR_OBJECT_H

#include <string>
#include <vector>
#include <map>
#include <set>

struct MonitorConnInfo {
    std::string protocol;
    std::string server;
    std::string uri;
    int port = 0;
    std::multimap<std::string, std::string> request_headers;
    uint8_t timeout_s = 3;
    uint8_t interval_s = 5;
};

class MonitorObject{
public:
    enum class Type{
        HTTP,
        PING,
        CONTENT
    };
    Type mon_type;
    std::string mon_id;

    MonitorConnInfo moncon;

    // For HTTP type monitor
    std::string search_string;
    std::vector<std::pair<std::string, std::string>> response_success_headers;
    std::set<int> success_codes;

    // For CONTENT type monitor;
    std::vector<MonitorConnInfo> mons;


    struct Hash { std::size_t operator()(const MonitorObject& mo) const { return std::hash<std::string>{}(mo.mon_id); } };
    struct Equal { bool operator()(const MonitorObject&mo1, const MonitorObject&mo2) const { return mo1.mon_id == mo2.mon_id; } };

    MonitorObject(){

    }
};




#endif //PINQUIRY_MONITOR_OBJECT_H
