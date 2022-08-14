//import * as http from "http";
//import https from "https";
import { axiosInstance } from "./axiosInstance";
//const httpAgent = new http.Agent({ keepAlive: true });
//const httpsAgent = new https.Agent({ keepAlive: true });
import { Button, message, Space } from "antd";
import md5 from "md5";

export async function loginUser({ name, pass }) {
  let username = "";
  let user_id = -1;
  let isAuthenticated = false;
  let isAdmin = false;

  console.log(md5(pass));

  await axiosInstance
    .post("/login", {
      username: name,
      password: md5(pass),
    })
    .then(function (response) {
      username = response.data.username;
      user_id = response.data.user_id;
      isAuthenticated = true;
      isAdmin = response.data.is_admin;
      console.log(response);
    })
    .catch(function (error) {
      console.log(error.response.data);
      if (error?.response?.data) {
        message.error(error.response.data);
      } else {
        message.error(
          "An error occurred with login, please contact a system administrator."
        );
      }
    })
    .then(function () {
      // always executed
    });

  return {
    username,
    user_id,
    isAuthenticated,
    isAdmin,
  };
}

export async function logoutUser() {
  await axiosInstance
    .get("/user_logout")
    .then(function (response) {
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    })
    .then(function () {
      // always executed
    });

  return false;
}
