//
// Created by ataya on 08-May-22.
//

#ifndef PINQUIRY_HTTP_CLIENT_H
#define PINQUIRY_HTTP_CLIENT_H

#include <string>
#include <map>

#define CPPHTTPLIB_OPENSSL_SUPPORT
#define CPPHTTPLIB_USE_POLL
#include "include/httplib.h"
#include <queue>

class HttpClient{
public:
    HttpClient() = delete;
    HttpClient(const HttpClient&) = delete;
    HttpClient(HttpClient&&) = default;
    HttpClient(const std::string& server, int req_timeout_ms);

    struct Response{
        uint64_t timestamp_ms;
        int status_code;
        std::string body;
        std::string error_message;
        long response_time_ms;
        std::vector<std::pair<std::string, std::string>> response_headers;
    };

    Response get(const std::string& uri,
                 const std::multimap<std::string, std::string>& headers = {});

    Response head(const std::string& uri,
                  const std::multimap<std::string, std::string>& headers = {});
    Response post_body(const std::string &uri, const std::multimap<std::string, std::string> &headers, const std::string &body);

    std::string get_ip();
    static std::string get_ip_from_socket(int sock);

private:
    static std::string get_certificate();
    static std::string get_error_message_httplib(httplib::Error result);

    std::string cert_location;
    std::string server_name;

    std::unique_ptr<httplib::Client> client = nullptr;

};

#endif //PINQUIRY_HTTP_CLIENT_H
