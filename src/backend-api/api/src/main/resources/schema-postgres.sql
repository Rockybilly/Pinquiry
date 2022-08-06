CREATE TABLE IF NOT EXISTS users(user_id serial PRIMARY KEY ,
username TEXT, user_password TEXT, email TEXT, signupDate TIMESTAMP );
CREATE TABLE IF NOT EXISTS monitors(mon_id serial PRIMARY KEY , type TEXT, mon_user int,
    CONSTRAINT fk_mon_user
    FOREIGN KEY(mon_user)
    REFERENCES users(user_id));
