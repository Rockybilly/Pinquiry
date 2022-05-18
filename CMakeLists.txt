cmake_minimum_required(VERSION 3.15)
project(Pinquiry)

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(test/monitor-service)


add_executable(Pinquiry src/monitor-service/main.cpp src/monitor-service/include/httplib.h src/monitor-service/http_client.cpp src/monitor-service/http_client.h)
target_link_libraries(Pinquiry ssl crypto)

