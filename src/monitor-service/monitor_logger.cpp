//
// Created by ataya on 21-Jul-22.
//

#include "monitor_logger.h"
#include <filesystem>

void MonitorLogger::initialize(const std::string &log_file_path) {
    std::filesystem::path path(log_file_path);
    std::filesystem::create_directories(path.parent_path());

    log_file.open(log_file_path, std::ofstream::out | std::ofstream::app);
    if(log_file.is_open()){
        log_file_absolute_path = log_file_path;
    }
    else{
        std::cerr << "Could not open log file!" << std::endl;
        exit(1);
    }
}

void MonitorLogger::log(const std::string &st, const std::string &end /* = "\n" */) {
    time_t curr_time;
    tm * curr_tm;
    char date_string[100];

    time(&curr_time);
    curr_tm = localtime(&curr_time);

    strftime(date_string, 100, "[%d/%b/%Y:%H:%M:%S %z]", curr_tm);

    m.lock();
    log_file << date_string << " " << st << std::endl;
    //log_file.flush();
    m.unlock();

}