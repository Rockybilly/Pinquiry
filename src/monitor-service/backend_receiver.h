//
// Created by ataya on 18-May-22.
//

#ifndef PINQUIRY_BACKEND_RECEIVER_H
#define PINQUIRY_BACKEND_RECEIVER_H


#include <string>
#include "include/httplib.h"

class BackendReceiver{
    std::string backend_ip;
    int backend_port = 0;
    void receiver_thread();
public:
    BackendReceiver(std::string ip, int port);
    void start_listen();
};

#endif //PINQUIRY_BACKEND_RECEIVER_H
