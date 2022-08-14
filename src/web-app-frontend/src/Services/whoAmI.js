//import * as http from "http";
//import https from "https";
import { axiosInstance } from "./axiosInstance";
//const httpAgent = new http.Agent({ keepAlive: true });
//const httpsAgent = new https.Agent({ keepAlive: true });

export async function whoAmI() {
  let username = "";
  let userId = -1;
  let isAuthenticated = false;
  let isAdmin = false;

  await axiosInstance
    .get("/whoami")
    .then(function (response) {
      username = response.data.username;
      userId = response.data.user_id;
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
    username,
    userId,
    isAuthenticated,
    isAdmin,
  };
}
