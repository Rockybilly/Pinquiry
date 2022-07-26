//
// Created by ataya on 21-Jul-22.
//

#ifndef PINQUIRY_MONITOR_LOGGER_H
#define PINQUIRY_MONITOR_LOGGER_H

#include <iostream>
#include <iomanip>
#include <ctime>
#include <fstream>
#include <mutex>

class MonitorLogger{

public:
    MonitorLogger() = default;
    void initialize(const std::string& log_file_path);
    void log(const std::string& st, const std::string& end = "\n");
    void log_reopen();
private:
    std::mutex m;
    std::ofstream log_file;
    std::string log_file_absolute_path;
};



#endif //PINQUIRY_MONITOR_LOGGER_H
