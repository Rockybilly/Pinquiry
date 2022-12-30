//
// Created by ataya on 19-May-22.
//

#ifndef PINQUIRY_MONITOR_RESULT_H
#define PINQUIRY_MONITOR_RESULT_H

#include <string>
#include <cstdlib>
#include "monitor_object.h"

struct MonitorResult{
    std::string mon_id;
    MonitorObject::Type mon_type;
    uint64_t timestamp_ms;
    virtual ~MonitorResult() = default;
    [[nodiscard]] std::string get_type_str() const{
        switch (mon_type) {
            case MonitorObject::Type::PING:
                return "ping";
            case MonitorObject::Type::HTTP:
                return "http";
            case MonitorObject::Type::CONTENT:
                return "content";
        }}
};

struct PingResult : MonitorResult{
    uint16_t response_time_ms;

    bool success;

    // In case of error
    ErrorString error_str;
    std::string traceroute;
};

struct HTTPResult : MonitorResult{
    std::string server_ip;
    uint16_t response_time_ms;
    int status_code;

    bool response_header_success;
    bool status_code_success;
    bool search_string_success;

    // In case of error
    ErrorString error_str;
    std::string traceroute;
    std::vector<std::pair<std::string, std::string>> response_headers;
};


struct ContentResult : MonitorResult{

    uint8_t num_of_groups = 0;
    bool status_code_success = true;

    struct SingleResult{

        std::string url;
        std::string server_ip;
        uint16_t response_time_ms;
        int status_code;
        uint64_t body_size;

        // In case of error
        std::vector<std::pair<std::string, std::string>> response_headers;
    };

    std::vector<std::vector<SingleResult>> groups;
};
#endif //PINQUIRY_MONITOR_RESULT_H
