#include <iostream>
#include "monitor_logger.h"

MonitorLogger logger;

int main() {
    logger.initialize("/var/log/pinquiry_monitor_service/general.log");
    return 0;
}
