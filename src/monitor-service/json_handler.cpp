//
// Created by ataya on 28-Jul-22.
//

#include "json_handler.h"


std::pair<std::string, ErrorString> json_parse_monitor_id(const std::string& body){

    rapidjson::Document d;

    if (d.Parse<0>( body.c_str() ).HasParseError() ){
        return  {"", "JSON parsing error."};
    }

    if(d.HasMember("mon_id") && d["mon_id"].IsString()){
        return {d["mon_id"].GetString(), ""};
    }
    else{
        return  {"", "Expected mon_id element."};
    }
}

ErrorString json_parse_into_mon_ping(const JsonObject& json_obj, MonitorObject& mon_obj){
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

ErrorString json_parse_into_mon_http(const JsonObject& json_obj, MonitorObject& mon_obj){

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
        return "Error, expected port item.";
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

    if(json_obj.HasMember("search_string") && json_obj["search_string"].IsString()){
        mon_obj.search_string = json_obj["search_string"].GetString();
    }

    return "";
}

ErrorString json_parse_into_mon_content(const JsonObject& json_obj, MonitorObject& mon_obj){

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

    if(json_obj.HasMember("content_locations") && json_obj["content_locations"].IsArray()){
        for(auto const& cl : json_obj["content_locations"].GetArray()){

            MonitorConnInfo moncon;

            if(cl.HasMember("protocol") && cl["protocol"].IsString()){
                moncon.protocol = cl["protocol"].GetString();
            }
            else{
                return "Error, expected protocol item.";
            }

            if(cl.HasMember("server") && cl["server"].IsString()){
                moncon.server = cl["server"].GetString();
            }
            else{
                return "Error, expected server(ip) item.";
            }

            if(cl.HasMember("uri") && cl["uri"].IsString()){
                moncon.uri = cl["uri"].GetString();
            }
            else{
                return "Error, expected uri item.";
            }

            if(cl.HasMember("port") && cl["port"].IsInt()){
                moncon.port = cl["port"].GetInt();
            }
            else{
                return "Error, expected port item.";
            }

            if(cl.HasMember("request_headers") && cl["request_headers"].IsObject()){
                for(auto it = cl["request_headers"].MemberBegin(); it != cl["request_headers"].MemberEnd(); it++) {
                    if (!it->name.IsString() || !it->value.IsString()) return "Error, expected string value for request_headers members.";
                    moncon.request_headers.insert({it->name.GetString(), it->value.GetString()});
                }
            }

            mon_obj.moncons.push_back(moncon);
        }


    }
    else{
        return "Error, expected content_locations item.";
    }



    return "";
}

std::pair<std::vector<MonitorObject>, ErrorString> json_parse_multiple_monitors(const std::string& body ){
    std::vector<MonitorObject> result;

    rapidjson::Document d;

    if (d.Parse<0>( body.c_str() ).HasParseError() ){
        return {{}, "JSON parsing error."};
    }

    if (d.HasMember("monitors_list") && d["monitors_list"].IsArray()){
        for(auto& mon : d["monitors_list"].GetArray()){
            MonitorObject mon_obj;
            ErrorString mon_parse_error = json_parse_monitor(mon, mon_obj);

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

std::pair<MonitorObject, ErrorString> json_parse_monitor(const std::string& body){

    rapidjson::Document d;

    if (d.Parse<0>( body.c_str() ).HasParseError() ){
        return {{}, "JSON parsing error."};
    }

    MonitorObject mon_obj;
    ErrorString mon_parse_error = json_parse_monitor(d, mon_obj);

    if (mon_parse_error.empty()){
        return {mon_obj, ""};
    }
    else{
        return {{}, mon_parse_error};
    }

}

ErrorString json_parse_monitor(const JsonObject& json_obj, MonitorObject& mon_obj){

    if(json_obj.HasMember("mon_id") && json_obj["mon_id"].IsString()){
        mon_obj.mon_id = json_obj["mon_id"].GetString();
    }
    else{
        return "Error, expected mon_id item.";
    }

    if(json_obj.HasMember("mon_type") && json_obj["mon_type"].IsString()){
        std::string mon_type = json_obj["mon_type"].GetString();
        ErrorString mon_parse_error;

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
    res_obj.AddMember("mon_type", rc->get_type_str(), allocator);
    res_obj.AddMember("timestamp_ms", rc->timestamp_ms, allocator);
    res_obj.AddMember("response_time_ms", rc->response_time_ms, allocator);

    res_obj.AddMember("error_str", rapidjson::StringRef(rc->error_str.c_str()), allocator);

    return res_obj;
}

JsonObject json_create_http_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    auto* rc = (HTTPResult*) result;
    rapidjson::Value res_obj;
    res_obj.SetObject();

    res_obj.AddMember("mon_id", rapidjson::StringRef(rc->mon_id), allocator);
    res_obj.AddMember("mon_type", rc->get_type_str(), allocator);
    res_obj.AddMember("timestamp_ms", rc->timestamp_ms, allocator);
    res_obj.AddMember("server_ip", rapidjson::StringRef(rc->server_ip), allocator);
    res_obj.AddMember("response_time_ms", rc->response_time_ms, allocator);
    res_obj.AddMember("status_code", rc->status_code, allocator);
    res_obj.AddMember("status_code_success", rc->status_code_success, allocator);
    res_obj.AddMember("response_header_success", rc->response_header_success, allocator);
    res_obj.AddMember("search_string_success", rc->search_string_success, allocator);

    if(!rc->error_str.empty()){
        rapidjson::Value debug_obj;
        debug_obj.SetObject();

        debug_obj.AddMember("error_str", rapidjson::StringRef(rc->error_str), allocator);

        rapidjson::Value response_headers;
        response_headers.SetObject();

        for(auto const& [k, v] : rc->response_headers){
            if (response_headers.HasMember(k)){
                response_headers[k].SetString(std::string(response_headers.GetString()) + ", " + v, allocator);
            }
            else{
                response_headers.AddMember(rapidjson::StringRef(k), rapidjson::StringRef(v), allocator);
            }
        }

        debug_obj.AddMember("response_headers", response_headers, allocator);
        res_obj.AddMember("debug_info", debug_obj, allocator);
    }

    return res_obj;
}

JsonObject json_create_content_result(const MonitorResult* result, rapidjson::Document::AllocatorType& allocator){
    auto* rc = (ContentResult*) result;
    rapidjson::Value res_obj;
    res_obj.SetObject();

    res_obj.AddMember("mon_id", rapidjson::StringRef(rc->mon_id), allocator);
    res_obj.AddMember("mon_type", rc->get_type_str(), allocator);
    res_obj.AddMember("num_of_groups", rc->num_of_groups, allocator);

    rapidjson::Value groups_arr(rapidjson::kArrayType);

    for(auto const& gs : rc->groups){
        rapidjson::Value group_arr(rapidjson::kArrayType);

        for(auto const& g : gs){
            rapidjson::Value single_obj;
            single_obj.SetObject();

            single_obj.AddMember("timestamp_ms", g.timestamp_ms,allocator);
            single_obj.AddMember("url", rapidjson::StringRef(g.url), allocator);
            single_obj.AddMember("server_ip", rapidjson::StringRef(g.server_ip), allocator);
            single_obj.AddMember("response_time_ms", g.response_time_ms, allocator);
            single_obj.AddMember("status_code", g.status_code, allocator);
            single_obj.AddMember("body_size", g.body_size, allocator);

            if(rc->num_of_groups > 1){
                rapidjson::Value debug_obj;
                debug_obj.SetObject();

                rapidjson::Value response_headers;
                response_headers.SetObject();

                for(auto const& [k, v] : g.response_headers){
                    if (response_headers.HasMember(k)){
                        response_headers[k].SetString(std::string(response_headers.GetString()) + ", " + v, allocator);
                    }
                    else{
                        response_headers.AddMember(rapidjson::StringRef(k), rapidjson::StringRef(v), allocator);
                    }
                }

                debug_obj.AddMember("response_headers", response_headers, allocator);
                single_obj.AddMember("debug_info", debug_obj, allocator);
            }

            group_arr.PushBack(single_obj, allocator);
        }
        groups_arr.PushBack(group_arr, allocator);
    }
    res_obj.AddMember("groups", groups_arr, allocator);

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