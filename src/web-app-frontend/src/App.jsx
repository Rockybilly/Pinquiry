import React, { useEffect } from "react";
import "./App.css";
import { action, createStore, StoreProvider } from "easy-peasy";

import { BrowserRouter as Router, Route, Link, Routes } from "react-router-dom";
import LandingPage from "./Pages/LandingPage";
import Dashboard from "./Pages/Dashboard";
import AddMonitor from "./Pages/AddMonitor";
import Monitors from "./Pages/Monitors";
import About from "./Pages/About";
import SecureRoute from "./Components/SecureRoute";

const store = createStore({
  user: {
    name: "",
    isAuthenticated: false,
  },

  setUser: action((state, payload) => {
    state.user = payload;
  }),
});

function App() {
  return (
    <StoreProvider store={store}>
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/about" element={<About />} />

          <Route
            path="/dashboard"
            element={
              <SecureRoute>
                <Dashboard />
              </SecureRoute>
            }
          />
          <Route
            path="/add-monitor"
            element={
              <SecureRoute>
                <AddMonitor />
              </SecureRoute>
            }
          />
          <Route
            path="/monitors"
            element={
              <SecureRoute>
                <Monitors />
              </SecureRoute>
            }
          />
        </Routes>
        }
      </Router>
    </StoreProvider>
  );
}

export default App;
