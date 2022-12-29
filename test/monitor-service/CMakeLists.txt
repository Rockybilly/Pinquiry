cmake_minimum_required(VERSION 3.15)
project(MonitorServiceUnitTest)

set(CMAKE_CXX_STANDARD 20)

include_directories(${gtest_SOURCE_DIR}/include ${gtest_SOURCE_DIR})
include_directories(../../src/monitor-service)
set(SOURCE_FILES ../../src/monitor-service/http_client.cpp)
add_executable(monitor_service_unit_tests_run unit_tests.cpp ${SOURCE_FILES})
target_link_libraries(monitor_service_unit_tests_run gtest gtest_main ssl crypto)