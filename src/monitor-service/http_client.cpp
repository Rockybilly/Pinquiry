//
// Created by ataya on 08-May-22.
//

#include "http_client.h"
#include <filesystem>
#include <memory>

HttpClient::HttpClient(const std::string &server, int timeout_ms) : server_name(server){

    if(cert_location.empty()){
        cert_location = get_certificate();
    }

    if (client == nullptr){
        client = std::make_unique<httplib::Client>(server);
        client->set_ca_cert_path(cert_location);
        client->set_read_timeout(0, timeout_ms * 1000);
        client->set_keep_alive(true);
    }
}

HttpClient::Response HttpClient::get(const std::string &uri,
                                           const std::multimap<std::string, std::string>& headers) {
    httplib::Headers hs;

    for(const auto& [k, v] : headers){
        hs.emplace(k, v);
    }

    auto t1 = std::chrono::high_resolution_clock::now();
    auto res = client->Get(uri, hs);
    auto t2 = std::chrono::high_resolution_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>((t2 - t1)).count();

    uint64_t timestamp = std::chrono::duration_cast<std::chrono::milliseconds>((t1.time_since_epoch())).count();

    if(res){
        std::vector<std::pair<std::string, std::string>> res_headers;
        for(const auto& [k, v] : res->headers){
            res_headers.emplace_back(k, v);
        }

        if (res->status == 200){
            return {timestamp, 200, std::string(res->body), "", elapsed, res_headers};
        }
        else{
            return {timestamp, res->status, "", "HTTP " + std::to_string(res->status), elapsed, res_headers};
        }
    }
    else{
        return {timestamp, 0, "", get_error_message_httplib(res.error()), elapsed, {}};
    }
}

HttpClient::Response HttpClient::head(const std::string &uri, const std::multimap<std::string, std::string>& headers){
    httplib::Headers hs;

    for(const auto& [k, v] : headers){
        hs.emplace(k, v);
    }

    auto t1 = std::chrono::high_resolution_clock::now();
    auto res = client->Head(uri, hs);
    auto t2 = std::chrono::high_resolution_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>((t2 - t1)).count();

    uint64_t timestamp = std::chrono::duration_cast<std::chrono::milliseconds>((t1.time_since_epoch())).count();

    if(res){
        std::vector<std::pair<std::string, std::string>> res_headers;
        for(const auto& [k, v] : res->headers){
            res_headers.emplace_back(k, v);
        }

        if (res->status == 200){
            return {timestamp, 200, "", "", elapsed, res_headers};
        }
        else{
            return {timestamp, res->status, "", "HTTP " + std::to_string(res->status), elapsed, res_headers};
        }
    }
    else{
        return {timestamp, 0, "", get_error_message_httplib(res.error()), elapsed, {}};
    }
}

HttpClient::Response HttpClient::post_body(const std::string &uri,
                                                 const std::multimap<std::string, std::string>& headers,
                                                 const std::string &body) {
    httplib::Headers hs;

    for(const auto& [k, v] : headers){
        hs.emplace(k, v);
    }
    auto t1 = std::chrono::high_resolution_clock::now();

    auto res = client->Post(uri, hs, body, "application/json");

    auto t2 = std::chrono::high_resolution_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>((t2 - t1)).count();
    uint64_t timestamp = std::chrono::duration_cast<std::chrono::milliseconds>((t1.time_since_epoch())).count();

    if(res){

        std::vector<std::pair<std::string, std::string>> res_headers;
        for(const auto& [k, v] : res->headers){
            res_headers.emplace_back(k, v);
        }

        if (res->status == 200){
            return {timestamp, 200, std::string(res->body), "", elapsed, res_headers};
        }
        else{
            return {timestamp, res->status, "", "HTTP " + std::to_string(res->status), elapsed, res_headers};
        }
    }
    else{
        return {timestamp, 0, "", get_error_message_httplib(res.error()), elapsed, {}};
    }

}

std::string HttpClient::get_error_message_httplib(httplib::Error result){
    switch (result) {
        case httplib::Error::Success:
            return "Success" ;
        case httplib::Error::Unknown:
            return "Unknown";
        case httplib::Error::Connection:
            return "Connection";
        case httplib::Error::BindIPAddress:
            return "BindIPAddress";
        case httplib::Error::Read:
            return "Read";
        case httplib::Error::Write:
            return "Write";
        case httplib::Error::ExceedRedirectCount:
            return "ExceedRedirectCount" ;
        case httplib::Error::Canceled:
            return "Canceled";
        case httplib::Error::SSLConnection:
            return "SSLConnection";
        case httplib::Error::SSLLoadingCerts:
            return "SSLLoadingCerts";
        case httplib::Error::SSLServerVerification:
            return "SSLServerVerification";
        case httplib::Error::UnsupportedMultipartBoundaryChars:
            return "UnsupportedMultipartBoundaryChars";
        case httplib::Error::Compression:
            return "Compression";
        default:
            return "";
    }
}

std::string HttpClient::get_certificate(){
    std::string result;
    static const std::vector<std::string> possible_files = {
            "/etc/ssl/certs/ca-certificates.crt",                // Debian/Ubuntu/Gentoo etc.
            "/etc/pki/tls/certs/ca-bundle.crt",                  // Fedora/RHEL 6
            "/etc/ssl/ca-bundle.pem",                            // OpenSUSE
            "/etc/pki/tls/cacert.pem",                           // OpenELEC
            "/etc/pki/ca-trust/extracted/pem/tls-ca-bundle.pem", // CentOS/RHEL 7
            "/etc/ssl/cert.pem"};

    for(const auto& st : possible_files){
        if (std::filesystem::exists(st)){
            result = st;
            break;
        }
    }

    if(!result.empty()){
        return result;
    }
    else{
        throw "Could not find a SSL certificate";
    }
}

std::string HttpClient::get_ip(){
        if(client)
            return HttpClient::get_ip_from_socket(client->socket());
        else
            return "";
}

std::string HttpClient::get_ip_from_socket(int sock){
    sockaddr_in addr{};
    socklen_t addr_len;
    getpeername(sock, (struct sockaddr *)&addr, &addr_len);

    char buf[17];
    inet_ntop(AF_INET, &(addr.sin_addr), buf, INET_ADDRSTRLEN);
    return {buf};
}
