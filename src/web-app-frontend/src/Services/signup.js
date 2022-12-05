//import * as http from "http";
//import https from "https";
import { axiosInstance } from "./axiosInstance";
import md5 from "md5";
//const httpAgent = new http.Agent({ keepAlive: true });
//const httpsAgent = new https.Agent({ keepAlive: true });

export async function signupUser({ username, password }) {
  let username_res = "";
  let user_id = -1;
  let isAuthenticated = false;
  let isAdmin = false;

  await axiosInstance
    .post("/sign-up", {
      username: username,
      password: md5(password),
    })
    .then(function (response) {
      username_res = response.data.username;
      user_id = response.data.user_id;
      isAuthenticated = true;
      isAdmin = response.data.is_admin;
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    })
    .then(function () {
      // always executed
    });

  return {
    username: username_res,
    user_id,
    isAuthenticated,
    isAdmin,
  };
}
