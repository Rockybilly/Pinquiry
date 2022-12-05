import React, { useEffect } from "react";
import "./App.css";
import { useStoreActions, useStoreState } from "easy-peasy";
import { history } from "./history";

import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes,
  unstable_HistoryRouter as HistoryRouter,
  Outlet,
} from "react-router-dom";
import LandingPage from "./Pages/LandingPage";
import MonitorApp from "./Pages/MonitorApp";
import Dashboard from "./Pages/Dashboard";
import AddMonitor from "./Pages/AddMonitor";
import Monitors from "./Pages/Monitors";
import About from "./Pages/About";
import SecureRoute from "./Components/SecureRoute";
import { loginUser } from "./Services/login";
import EditProfile from "./Pages/EditProfile";
import AdminSecureRoute from "./Components/adminSecureRoute";
import UserOperations from "./Pages/UserOperations";
import { Spin } from "antd";
import MonitorServices from "./Pages/MonitorServices";
import MonitorDetail from "./Pages/MonitorDetail";

function App() {
  const doWhoAmI = useStoreActions((actions) => actions.doWhoAmI);
  const setLoginLoading = useStoreActions((actions) => actions.setLoginLoading);
  const loginLoading = useStoreState((state) => state.loginLoading);

  useEffect(() => {
    doWhoAmI();
  }, []);

  return (
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
          <Route path="edit-profile" element={<EditProfile />} />
          <Route path="monitor-detail/:monId" element={<MonitorDetail />} />

          <Route
            element={
              <AdminSecureRoute>
                <Outlet />
              </AdminSecureRoute>
            }
          >
            <Route path="user-operations" element={<UserOperations />} />
            <Route path="monitor-services" element={<MonitorServices />} />
          </Route>
        </Route>
      </Routes>
    </HistoryRouter>
  );
}

export default App;
