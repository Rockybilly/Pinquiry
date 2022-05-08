cmake_minimum_required(VERSION 3.15)
project(Pinquiry)

set(CMAKE_CXX_STANDARD 20)

add_executable(Pinquiry src/monitor-service/main.cpp src/monitor-service/include/httplib.h)
