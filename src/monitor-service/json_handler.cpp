//
// Created by ataya on 28-Jul-22.
//

#include "json_handler.h"


std::string json_parse_into_mon_ping(const JsonObject& json_obj, MonitorObject& mon_obj){
    if(json_obj.HasMember("server") && json_obj["server"].IsString()){
        mon_obj.moncon.server = json_obj["server"].GetString();
    }
    else{
        return "Error, expected server(ip) item.";
    }

    if(json_obj.HasMember("timeout_s") && json_obj["timeout_s"].IsInt()){
        mon_obj.moncon.timeout_s = json_obj["timeout_s"].GetInt();
    }
    else{
        return "Error, expected timeout_s item.";
    }

    if(json_obj.HasMember("interval_s") && json_obj["interval_s"].IsInt()){
        mon_obj.moncon.interval_s = json_obj["interval_s"].GetInt();
    }
    else{
        return "Error, expected interval_s item.";
    }

    return "";
}

std::string json_parse_into_mon_http(const JsonObject& json_obj, MonitorObject& mon_obj){

    if(json_obj.HasMember("protocol") && json_obj["protocol"].IsString()){
        mon_obj.moncon.protocol = json_obj["protocol"].GetString();
    }
    else{
        return "Error, expected protocol item.";
    }

    if(json_obj.HasMember("server") && json_obj["server"].IsString()){
        mon_obj.moncon.server = json_obj["server"].GetString();
    }
    else{
        return "Error, expected server(ip) item.";
    }

    if(json_obj.HasMember("uri") && json_obj["uri"].IsString()){
        mon_obj.moncon.uri = json_obj["uri"].GetString();
    }
    else{
        return "Error, expected uri item.";
    }

    if(json_obj.HasMember("port") && json_obj["port"].IsInt()){
        mon_obj.moncon.port = json_obj["port"].GetInt();
    }
    else{
        return "Error, expected int item.";
    }

    if(json_obj.HasMember("request_headers") && json_obj["request_headers"].IsObject()){
        for(auto it = json_obj["request_headers"].MemberBegin(); it != json_obj["request_headers"].MemberEnd(); it++) {
            if (!it->name.IsString() || !it->value.IsString()) return "Error, expected string value for request_headers members.";
            mon_obj.moncon.request_headers.insert({it->name.GetString(), it->value.GetString()});
        }
    }


    if(json_obj.HasMember("success_codes") && json_obj["success_codes"].IsArray()){
        for(auto& code : json_obj["success_codes"].GetArray()) {
            if (!code.IsInt()) return "Error, expected int value for success_codes members.";
            mon_obj.success_codes.insert(code.GetInt());
        }
    }
    else{
        return "Error, expected success_codes item.";
    }

    if(json_obj.HasMember("success_headers") && json_obj["success_headers"].IsObject()){
        for(auto it = json_obj["success_headers"].MemberBegin(); it != json_obj["success_headers"].MemberEnd(); it++) {
            if (!it->name.IsString() || !it->value.IsString()) return "Error, expected string value for success_headers members.";
            mon_obj.response_success_headers.emplace_back(it->name.GetString(), it->value.GetString());
        }
    }

    if(json_obj.HasMember("timeout_s") && json_obj["timeout_s"].IsInt()){
        mon_obj.moncon.timeout_s = json_obj["timeout_s"].GetInt();
    }
    else{
        return "Error, expected timeout_s item.";
    }

    if(json_obj.HasMember("interval_s") && json_obj["interval_s"].IsInt()){
        mon_obj.moncon.interval_s = json_obj["interval_s"].GetInt();
    }
    else{
        return "Error, expected interval_s item.";
    }

    if(json_obj.HasMember("search_string") && json_obj["search_string"].IsString()){
        mon_obj.search_string = json_obj["search_string"].GetString();
    }

    return "";
}

std::string json_parse_into_mon_content(const JsonObject& json_obj, MonitorObject& mon_obj){
    return "";
}

std::pair<std::vector<MonitorObject>, std::string> json_parse_multiple_monitors(const std::string& body ){
    std::vector<MonitorObject> result;

    rapidjson::Document d;

    if (d.Parse<0>( body.c_str() ).HasParseError() ){
        return {{}, "JSON parsing error."};
    }

    if (d.HasMember("monitors_list") && d["monitors_list"].IsArray()){
        for(auto& mon : d["monitors_list"].GetArray()){
            MonitorObject mon_obj;
            std::string mon_parse_error = json_parse_monitor(mon, mon_obj);

            if (!mon_parse_error.empty()){
                return {{}, mon_parse_error};
            }

            result.push_back(mon_obj);
        }
    }
    else{
        return {{}, "Error, expected monitors_list item."};
    }

    return {result, ""};
}

std::pair<MonitorObject, std::string> json_parse_monitor(const std::string& body){

    rapidjson::Document d;

    if (d.Parse<0>( body.c_str() ).HasParseError() ){
        return {{}, "JSON parsing error."};
    }

    MonitorObject mon_obj;
    std::string mon_parse_error = json_parse_monitor(d, mon_obj);

    if (mon_parse_error.empty()){
        return {mon_obj, ""};
    }
    else{
        return {{}, ""};
    }

}

std::string json_parse_monitor(const JsonObject& json_obj, MonitorObject& mon_obj){

    if(json_obj.HasMember("mon_id") && json_obj["mon_id"].IsString()){
        mon_obj.mon_id = json_obj["mon_id"].GetString();
    }
    else{
        return "Error, expected mon_id item.";
    }

    if(json_obj.HasMember("mon_type") && json_obj["mon_type"].IsString()){
        std::string mon_type = json_obj["mon_type"].GetString();
        std::string mon_parse_error;

        if (mon_type == "ping"){
            mon_obj.mon_type = MonitorObject::Type::PING;
            mon_parse_error = json_parse_into_mon_ping(json_obj, mon_obj);
        }
        else if (mon_type == "http"){
            mon_obj.mon_type = MonitorObject::Type::HTTP;
            mon_parse_error = json_parse_into_mon_http(json_obj, mon_obj);
        }
        else if (mon_type == "content"){
            mon_obj.mon_type = MonitorObject::Type::CONTENT;
            mon_parse_error = json_parse_into_mon_content(json_obj, mon_obj);
        }
        else{
            return "Unrecognized monitor type: " + mon_type;
        }

        if (!mon_parse_error.empty()){
            return mon_parse_error;
        }

    }
    else{
        return "Error, expected mon_id item.";
    }

    return "";


}

JsonObject json_create_ping_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    auto* rc = (PingResult*) result;
    rapidjson::Value res_obj;
    res_obj.SetObject();

    res_obj.AddMember("mon_id", rapidjson::StringRef(rc->mon_id.c_str()), allocator);
    res_obj.AddMember("timestamp_ms", rc->timestamp_ms, allocator);
    res_obj.AddMember("response_time_ms", rc->response_time_ms, allocator);

    res_obj.AddMember("error_str", rapidjson::StringRef(rc->error_str.c_str()), allocator);

    return res_obj;
}

JsonObject json_create_http_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    auto* rc = (HTTPResult*) result;
    rapidjson::Value res_obj;
    res_obj.SetObject();

    res_obj.AddMember("mon_id", rapidjson::StringRef(rc->mon_id.c_str()), allocator);
    res_obj.AddMember("timestamp_ms", rc->timestamp_ms, allocator);
    res_obj.AddMember("response_time_ms", rc->response_time_ms, allocator);
    res_obj.AddMember("status_code", rc->status_code, allocator);
    res_obj.AddMember("status_code_success", rc->status_code_success, allocator);
    res_obj.AddMember("response_header_success", rc->response_header_success, allocator);
    res_obj.AddMember("search_string_success", rc->search_string_success, allocator);
    res_obj.AddMember("error_str", rapidjson::StringRef(rc->error_str.c_str()), allocator);

    return res_obj;
}

JsonObject json_create_content_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    auto* rc = (ContentResult*) result;
    rapidjson::Value res_obj;
    res_obj.SetObject();

    res_obj.AddMember("mon_id", rapidjson::StringRef(rc->mon_id.c_str()), allocator);
    res_obj.AddMember("timestamp_ms", rc->timestamp_ms, allocator);


    return res_obj;
}

JsonObject json_create_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    switch (result->mon_type){
        case MonitorObject::Type::PING:
            return json_create_ping_result(result, allocator);
        case MonitorObject::Type::HTTP:
            return json_create_http_result(result, allocator);
        case MonitorObject::Type::CONTENT:
            return json_create_content_result(result, allocator);
    }
}

std::string json_create_multiple_results(const std::vector<MonitorResult*>& results){
    rapidjson::Document d;
    d.SetObject();
    rapidjson::Document::AllocatorType& allocator = d.GetAllocator();

    rapidjson::Value results_array(rapidjson::kArrayType);

    for(auto const& r : results){
        results_array.PushBack(json_create_result(r, allocator), allocator);
    }

    d.AddMember("results", results_array, allocator);

    rapidjson::StringBuffer strbuf;
    rapidjson::PrettyWriter<rapidjson::StringBuffer> writer(strbuf);
    d.Accept(writer);

    return strbuf.GetString();
}