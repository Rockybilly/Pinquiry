//
// Created by ataya on 26-Jul-22.
//

#ifndef PINQUIRY_JSON_HANDLER_H
#define PINQUIRY_JSON_HANDLER_H

#define RAPIDJSON_HAS_STDSTRING 1
#include "include/rapidjson/rapidjson.h"
#include "include/rapidjson/document.h"
#include "include/rapidjson/stringbuffer.h"
#include "include/rapidjson/prettywriter.h"
#include "monitor_result.h"
#include <vector>

typedef rapidjson::GenericValue<rapidjson::UTF8<char>, rapidjson::MemoryPoolAllocator<rapidjson::CrtAllocator>> JsonObject;

std::string json_parse_into_mon_ping(const JsonObject& json_obj, MonitorObject& mon_obj);

std::string json_parse_into_mon_http(const JsonObject& json_obj, MonitorObject& mon_obj);

std::string json_parse_into_mon_content(const JsonObject& json_obj, MonitorObject& mon_obj);

std::pair<std::vector<MonitorObject>, std::string> json_parse_multiple_monitors(const std::string& body );

std::pair<MonitorObject, std::string> json_parse_monitor(const std::string& body);

std::string json_parse_monitor(const JsonObject& json_obj, MonitorObject& mon_obj);

std::string json_create_multiple_results(const std::vector<MonitorResult*>& results);


#endif //PINQUIRY_JSON_HANDLER_H
