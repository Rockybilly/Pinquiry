//
// Created by ataya on 08-May-22.
//

#ifndef PINQUIRY_HTTP_CLIENT_H
#define PINQUIRY_HTTP_CLIENT_H

#include <string>
#include <map>

#define CPPHTTPLIB_OPENSSL_SUPPORT
#include "include/httplib.h"

class HttpClient{
public:
    HttpClient() = delete;
    HttpClient(const std::string& server, int req_timeout_sec);
    ~HttpClient();

    struct Response{
        int status_code;
        std::string body;
        std::string error_message;
        long response_time_ms;
    };


    Response get(const std::string& uri,
                 const std::multimap<std::string, std::string>& headers = {});

    Response head(const std::string& uri,
                  const std::multimap<std::string, std::string>& headers = {});

    static std::string get_error_message_httplib(httplib::Error result);
private:
    std::string get_certificate();

    std::string cert_location;
    std::string server_name;

    httplib::Client* client = nullptr;
};

#endif //PINQUIRY_HTTP_CLIENT_H
