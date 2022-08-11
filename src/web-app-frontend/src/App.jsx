import React, { useEffect } from "react";
import "./App.css";
import { action, createStore, StoreProvider, thunk, debug } from "easy-peasy";
import { history } from "./history";

import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes,
  unstable_HistoryRouter as HistoryRouter,
} from "react-router-dom";
import LandingPage from "./Pages/LandingPage";
import MonitorApp from "./Pages/MonitorApp";
import Dashboard from "./Pages/Dashboard";
import AddMonitor from "./Pages/AddMonitor";
import Monitors from "./Pages/Monitors";
import About from "./Pages/About";
import SecureRoute from "./Components/SecureRoute";
import { loginUser } from "./Services/login";

const store = createStore(
  {
    user: {
      name: "",
      isAuthenticated: false,
    },

    loginLoading: false,

    setUser: action((state, payload) => {
      state.user = payload;
    }),

    setLoginLoading: action((state, payload) => {
      state.loginLoading = payload;
    }),

    doLogin: thunk(async (actions, payload) => {
      actions.setLoginLoading(true);

      const user = await loginUser(payload);
      if (user.token !== "") {
        actions.setUser(user);

        history.push("/dashboard");
      }
      actions.setLoginLoading(false);
    }),
  },
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

function App() {
  return (
    <StoreProvider store={store}>
      <HistoryRouter history={history}>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/about" element={<About />} />
          <Route
            element={
              <SecureRoute>
                <MonitorApp />
              </SecureRoute>
            }
          >
            <Route index path="dashboard" element={<Dashboard />} />
            <Route path="add-monitor" element={<AddMonitor />} />
            <Route path="monitors" element={<Monitors />} />
          </Route>
        </Routes>
      </HistoryRouter>
    </StoreProvider>
  );
}

export default App;
