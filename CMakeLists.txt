cmake_minimum_required(VERSION 3.15)
project(Pinquiry)

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(test/monitor-service)


add_executable(Pinquiry src/monitor-service/json_handler.cpp src/monitor-service/json_handler.h src/monitor-service/ping_client.cpp src/monitor-service/ping_client.h src/monitor-service/main.cpp src/monitor-service/include/httplib.h src/monitor-service/http_client.cpp src/monitor-service/http_client.h src/monitor-service/monitor_object.h src/monitor-service/monitor_watcher.cpp src/monitor-service/monitor_watcher.h src/monitor-service/backend_receiver.cpp src/monitor-service/backend_receiver.h src/monitor-service/monitor_service.cpp src/monitor-service/monitor_service.h src/monitor-service/backend_client.cpp src/monitor-service/backend_client.h src/monitor-service/monitor_result.h src/monitor-service/monitor_logger.cpp src/monitor-service/monitor_logger.h)
target_link_libraries(Pinquiry ssl crypto pthread)

