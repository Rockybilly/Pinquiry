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

std::pair<std::string, ErrorString> json_parse_monitor_id(const std::string& body);

ErrorString json_parse_into_mon_ping(const JsonObject& json_obj, MonitorObject& mon_obj);

ErrorString json_parse_into_mon_http(const JsonObject& json_obj, MonitorObject& mon_obj);

ErrorString json_parse_into_mon_content(const JsonObject& json_obj, MonitorObject& mon_obj);

std::pair<std::vector<MonitorObject>, ErrorString> json_parse_multiple_monitors(const std::string& body );

std::pair<MonitorObject, ErrorString> json_parse_monitor(const std::string& body);

ErrorString json_parse_monitor(const JsonObject& json_obj, MonitorObject& mon_obj);

JsonObject json_create_ping_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator);
JsonObject json_create_http_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator);
JsonObject json_create_content_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator);
JsonObject json_create_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator);
std::string json_create_multiple_results(const std::vector<MonitorResult*>& results);

#endif //PINQUIRY_JSON_HANDLER_H
