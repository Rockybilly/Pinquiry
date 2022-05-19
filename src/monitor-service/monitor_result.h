//
// Created by ataya on 19-May-22.
//

#ifndef PINQUIRY_MONITOR_RESULT_H
#define PINQUIRY_MONITOR_RESULT_H

#include <string>
#include <cstdlib>

struct MonitorResult{
    uint16_t response_time_ms;

    uint8_t http_code;
    std::string error_str;

    bool success;
};

#endif //PINQUIRY_MONITOR_RESULT_H
