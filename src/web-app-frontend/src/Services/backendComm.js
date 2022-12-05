import { axiosInstance } from "./axiosInstance";
import { message } from "antd";

const fakeRequest = (value) =>
  new Promise((resolve) => {
    setTimeout(() => resolve(value), 500);
  });

const md5 = require("md5");

export async function getUserList() {
  let result = [];

  await axiosInstance
    .get("/admin/showUsers")
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      console.log(error.response.data);
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
      } else {
        message.error(
          "Error fetching data. Please consult a system administrator."
        );
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function deleteUserAdmin({ user_id }) {
  let result = false;

  await axiosInstance
    .post("/admin/delete-user", { user_id })
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      console.log(error);

      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
      } else {
        message.error("Error deleting. Please consult a system administrator.");
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function deleteUser({ password }) {
  await axiosInstance
    .post("/delete-user", { password: md5(password) })
    .then(function (response) {
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error deleting. Please consult a system administrator.");
        throw "Error deleting. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function getUserMonitors({ page_size, page_no }) {
  let result = {};
  await axiosInstance
    .get("/get-monitors", { params: { page_size, page_no } })
    .then(function (response) {
      if (response.data) {
        result = response.data;
      } else {
        result = [];
      }
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error fetching monitors. Please consult a system administrator."
        );
        throw "Error fetching monitors. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function updatePassword({ current_password, new_password }) {
  await axiosInstance
    .post("/update-password", {
      current_password: md5(current_password),
      new_password: md5(new_password),
    })
    .then(function (response) {
      message.info("Update completed.");
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error Updating. Please consult a system administrator.");
        throw "Error Updating. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function addMonitor(mon_object) {
  await axiosInstance
    .post("/add-monitor", mon_object)
    .then(function (response) {
      message.info("Monitor added.");
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error adding. Please consult a system administrator.");
        throw "Error adding. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function removeMonitor(mon_id) {
  await axiosInstance
    .post("/remove-monitor", { id: mon_id })
    .then(function (response) {
      message.info("Monitor removed.");
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error removing. Please consult a system administrator.");
        throw "Error removing. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function addService(serviceObject) {
  await axiosInstance
    .post("/admin/add-service-worker", serviceObject)
    .then(function (response) {
      message.info("Service worker added.");
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error adding. Please consult a system administrator.");
        throw "Error adding. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function getServices() {
  let result = [];
  await axiosInstance
    .get("/admin/list-service-workers")
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error getting services. Please consult a system administrator."
        );
        throw "Error getting services. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function removeService({ ip }) {
  await axiosInstance
    .post("/admin/remove-service-worker", { ip })
    .then(function (response) {
      message.info("Service worker removed.");
      return response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error("Error removing. Please consult a system administrator.");
        throw "Error removing. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });
}

export async function getDashboardStatistics() {
  let result = [];
  await axiosInstance
    .get("/dashboard-statistics")
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error getting statistics. Please consult a system administrator."
        );
        throw "Error getting statistics. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function setEmail({ email }) {
  let result = [];
  await axiosInstance
    .post("/update-email", { email })
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error setting email. Please consult a system administrator."
        );
        throw "Error setting email. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function getMonIncidentCountRange({ id, begin, end, interval }) {
  let result = [];
  await axiosInstance
    .get("/get-monitor-incident-counts/" + id, {
      params: { begin, end, interval },
    })
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error getting incident data count. Please consult a system administrator."
        );
        throw "Error getting incident data count. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}

export async function getMonResponseTimeRange({ id, begin, end, interval }) {
  let result = [];
  await axiosInstance
    .get("/get-monitor-response-times/" + id, {
      params: { begin, end, interval },
    })
    .then(function (response) {
      result = response.data;
    })
    .catch(function (error) {
      if (
        (error?.response?.data && typeof error.response.data === "string") ||
        error.response.data instanceof String
      ) {
        message.error(error.response.data);
        throw error.response.data;
      } else {
        message.error(
          "Error getting response time data. Please consult a system administrator."
        );
        throw "Error getting response time data. Please consult a system administrator.";
      }
    })
    .then(function () {
      // always executed
    });

  return result;
}
