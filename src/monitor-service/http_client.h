//
// Created by ataya on 08-May-22.
//

#ifndef PINQUIRY_HTTP_CLIENT_H
#define PINQUIRY_HTTP_CLIENT_H

#include <string>
#include <map>

#define CPPHTTPLIB_OPENSSL_SUPPORT
#include "include/httplib.h"
#include <queue>

class HttpClient{
public:
    HttpClient() = delete;
    HttpClient(const std::string& server, int req_timeout_ms, int connection_count);
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

private:
    httplib::Client* acquire_client();
    void release_client(httplib::Client* cli);
    static std::string get_certificate();
    static std::string get_error_message_httplib(httplib::Error result);

    std::string cert_location;
    std::string server_name;

    int connection_count;
    //drogon::HttpClientPtr api_connector;
    std::queue<httplib::Client*> connection_queue;
    std::mutex queue_mutex;
    std::counting_semaphore<64> queue_sem;

    httplib::Client* client = nullptr;
};

#endif //PINQUIRY_HTTP_CLIENT_H
