import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { action, createStore, StoreProvider, thunk } from "easy-peasy";
import { loginUser, logoutUser } from "./Services/login";
import { history } from "./history";
import { whoAmI } from "./Services/whoAmI";
import { signupUser } from "./Services/signup";
import { useLocation } from "react-router-dom";

const store = createStore({
  user: {
    username: "",
    userId: "",
    isAuthenticated: false,
    isAdmin: false,
  },

  loginLoading: true,

  setUser: action((state, payload) => {
    state.user = payload;
  }),

  setLoginLoading: action((state, payload) => {
    state.loginLoading = payload;
  }),

  doLogin: thunk(async (actions, payload) => {
    actions.setLoginLoading(true);

    const user = await loginUser(payload);
    if (user.isAuthenticated) {
      actions.setUser(user);
      console.log(user);
      history.push("/dashboard");
    }
    actions.setLoginLoading(false);
  }),
  doSignup: thunk(async (actions, payload) => {
    actions.setLoginLoading(true);

    const user = await signupUser(payload);
    if (user.isAuthenticated) {
      actions.setUser(user);
      console.log(user);
      history.push("/dashboard");
    }
    actions.setLoginLoading(false);
  }),

  doWhoAmI: thunk(async (actions, payload) => {
    actions.setLoginLoading(true);

    const user = await whoAmI(payload);
    if (user.isAuthenticated) {
      actions.setUser(user);
      console.log(user);
    }
    actions.setLoginLoading(false);
  }),

  doLogout: thunk(async (actions) => {
    actions.setLoginLoading(true);

    const isAuthenticated = await logoutUser();

    actions.setUser({
      username: "",
      userId: "",
      isAuthenticated: false,
      isAdmin: false,
    });

    history.push("/");

    actions.setLoginLoading(false);
  }),
});

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <StoreProvider store={store}>
    <App />
  </StoreProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
