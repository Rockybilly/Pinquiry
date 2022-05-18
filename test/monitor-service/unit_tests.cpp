//
// Created by ataya on 17-May-22.
//

#include "gtest/gtest.h"

#include "http_client.h"

class ServerEnvironment : public ::testing::Environment {
    std::thread server_thread;
    httplib::Server* test_server = nullptr;
public:

    ~ServerEnvironment() override = default;

    void SetUp() override {
        test_server = new httplib::Server();

        test_server->Get("/hi", [](const httplib::Request &, httplib::Response &res) {
            return 200;
        });


        server_thread = std::thread(&httplib::Server::listen, test_server, "127.0.0.1", 9999, 0);
    }

    void TearDown() override {
        test_server->stop();
        server_thread.join();
    }
};

testing::Environment* const env = testing::AddGlobalTestEnvironment(new ServerEnvironment);

/*
TEST(HttpClientTest, GetRequest) {
    HttpClient cli("127.0.0.1:9999", 100, 1);
    for (int i = 0; i < 500; i++) {
        auto r = cli.get("/hi");
        EXPECT_EQ(r.status_code, 200) << r.error_message;
    }

    for (int i = 0; i < 500; i++) {
        auto r = cli.get("/hi2");
        EXPECT_EQ(r.status_code, 404) << r.error_message;
    }
}*/

TEST(HttpClientMultipleTest, GetRequest) {
    HttpClient cli("127.0.0.1:9999", 100, 8);

    for (int i = 0; i < 100; i++) {
        auto r = cli.get("/hi");
        EXPECT_EQ(r.status_code, 200) << r.error_message;
    }

    for (int i = 0; i < 100; i++) {
        auto r = cli.get("/hi2");
        EXPECT_EQ(r.status_code, 404) << r.error_message;
    }
}