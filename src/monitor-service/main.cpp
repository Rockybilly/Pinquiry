#include <iostream>
#include "monitor_logger.h"
#include "monitor_service.h"

MonitorLogger logger;

int main() {
    logger.initialize("/var/log/pinquiry_monitor_service/general.log");
    MonitorService ms("127.0.0.1", 3333);
    ms.begin_service();


    return 0;
}
