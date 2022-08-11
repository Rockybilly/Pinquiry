#include <iostream>
#include "monitor_logger.h"
#include "monitor_service.h"

MonitorLogger logger;

int main(int argc, char* argv[]) {

    if(argc != 3){
        std::cout << "Usage ./Pinquiry backend_ip backend_port" << std::endl;
        exit(1);
    }

    logger.initialize("/var/log/pinquiry_monitor_service/general.log");
    MonitorService ms(argv[1], std::atoi(argv[2]));
    ms.begin_service();


    return 0;
}
