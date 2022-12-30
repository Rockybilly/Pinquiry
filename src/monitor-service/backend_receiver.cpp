//
// Created by ataya on 18-May-22.
//

#include "backend_receiver.h"
#include "json_handler.h"

BackendReceiver::BackendReceiver(std::string ip,
                                 const std::function< ErrorString(const MonitorObject&)>& add_mon_handler,
                                 const std::function< ErrorString(const std::string&)>& remove_mon_handler,
                                 const std::function< ErrorString(const MonitorObject&)>& update_mon_handler)
                                 : backend_ip(std::move(ip)), add_monitor_handler(add_mon_handler),
                                 remove_monitor_handler(remove_mon_handler),
                                 update_monitor_handler(update_mon_handler){

    svr.Post( "/add_monitor", [&](const httplib::Request& req, httplib::Response& res) {
        std::cout << "API: /add_monitor called" << std::endl;
        auto [mon, error_st] = json_parse_monitor(req.body);

        if (error_st.empty()){
            auto error_st_handler = add_monitor_handler(mon);

            if (error_st_handler.empty()){
                res.status = 200;
            }
            else{
                res.status = 404;
                res.set_content(error_st_handler, "text/plain");
            }
        }
        else{
            res.status = 404;
            res.set_content(error_st, "text/plain");
        }
    });

    svr.Post( "/remove_monitor", [&](const httplib::Request& req, httplib::Response& res) {
        std::cout << "API: /remove_monitor called" << std::endl;
        auto [mon_id, error_st] = json_parse_monitor_id(req.body);

        if (error_st.empty()){
            auto error_st_handler = remove_monitor_handler(mon_id);

            if (error_st_handler.empty()){
                res.status = 200;
            }
            else{
                res.status = 404;
                res.set_content(error_st_handler, "text/plain");
            }
        }
        else{
            res.status = 404;
            res.set_content(error_st, "text/plain");
        }
    });

    svr.Post( "/update_monitor", [&](const httplib::Request& req, httplib::Response& res) {
        std::cout << "API: /update_monitor called" << std::endl;
        auto [mon, error_st] = json_parse_monitor(req.body);

        if (error_st.empty()){
            auto error_st_handler = update_monitor_handler(mon);

            if (error_st_handler.empty()){
                res.status = 200;
            }
            else{
                res.status = 404;
                res.set_content(error_st_handler, "text/plain");
            }
        }
        else{
            res.status = 404;
            res.set_content(error_st, "text/plain");
        }
    });

    svr.Get( "/i_am_online", [&](const httplib::Request& req, httplib::Response& res) {
        res.status = 200;
        res.set_content("", "text/plain");
    });
}

void BackendReceiver::receiver_worker(){
    std::cout << "Monitor listening 6363 port" << std::endl;
    while (true){
        svr.listen("0.0.0.0", 6363);
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void BackendReceiver::start_listen(){
    std::thread(&BackendReceiver::receiver_worker, this).detach();
}