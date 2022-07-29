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
};

struct PingResult : MonitorResult{
    uint64_t timestamp_ms;
    uint16_t response_time_ms;

    bool success;

    // In case of error
    std::string error_str;
};

struct HTTPResult : MonitorResult{
    uint64_t timestamp_ms;
    uint16_t response_time_ms;
    uint8_t status_code;

    bool response_header_success;
    bool status_code_success;

    // In case of error
    std::string error_str;
    std::vector<std::pair<std::string, std::string>> response_headers;
};

struct ContentResult : MonitorResult{
    std::vector<uint64_t> timestamp_ms;
    std::vector<std::string> error_str;
    bool success;
};
#endif //PINQUIRY_MONITOR_RESULT_H
