CREATE SCHEMA IF NOT EXISTS pinquiry;
CREATE TYPE pinquiry.user_role AS ENUM ('ADMIN', 'USER');
CREATE TYPE pinquiry.monitor_type AS ENUM ('HTTP', 'PING', 'CONTENT');
CREATE TYPE pinquiry.content_protocol_type AS ENUM ('HTTP', 'HTTPS');
CREATE TYPE pinquiry.http_protocol_type AS ENUM ('HTTP', 'HTTPS');

CREATE TABLE IF NOT EXISTS pinquiry.users(user_id serial PRIMARY KEY ,
username TEXT, user_password TEXT, email TEXT, signupDate TIMESTAMP , role user_role );

CREATE TABLE IF NOT EXISTS pinquiry.ping_monitors(id serial PRIMARY KEY , type monitor_type,
                            mon_user int, timeout_s int, interval_s int, server TEXT,
    CONSTRAINT fk_mon_user
    FOREIGN KEY(mon_user)
    REFERENCES pinquiry.users(user_id) );

CREATE TABLE IF NOT EXISTS pinquiry.http_monitors(id serial PRIMARY KEY , type monitor_type,
                                         mon_user int, timeout_s int, interval_s int, protocol http_protocol_type, server TEXT, uri TEXT,
                                         port int, request_headers oid, response_headers json, success_codes json,
                                         CONSTRAINT fk_mon_user
                                             FOREIGN KEY(mon_user)
                                                 REFERENCES pinquiry.users(user_id) );



CREATE TABLE IF NOT EXISTS pinquiry.ping_monitors(id serial PRIMARY KEY , type monitor_type,
                                         mon_user int, timeout_s int, interval_s int, server TEXT,
                                         CONSTRAINT fk_mon_user
                                             FOREIGN KEY(mon_user)
                                                 REFERENCES pinquiry.users(user_id) );

CREATE TABLE IF NOT EXISTS pinquiry.content_monitors(id serial PRIMARY KEY , type monitor_type,
                                         mon_user int, timeout_s int, interval_s int,
                                         CONSTRAINT fk_mon_user
                                             FOREIGN KEY(mon_user)
                                                 REFERENCES pinquiry.users(user_id) );

CREATE TABLE IF NOT EXISTS pinquiry.content_monitor_info(id serial PRIMARY KEY , protocol content_protocol_type, uri TEXT,
                                         port int, request_headers oid, mon_id int,
                                         CONSTRAINT fk_content_monitor
                                             FOREIGN KEY(mon_id)
                                                 REFERENCES pinquiry.monitor(id) );
