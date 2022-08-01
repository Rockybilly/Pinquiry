CREATE TABLE IF NOT EXISTS users(user_id serial PRIMARY KEY , username TEXT, user_password TEXT, email TEXT, signupDate TIMESTAMP, monitors TEXT[]);
