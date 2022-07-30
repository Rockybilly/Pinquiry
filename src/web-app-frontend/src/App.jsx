import React from 'react';
import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Routes
} from "react-router-dom";
import LandingPage from './Pages/LandingPage';
import Dashboard from './Pages/Dashboard';
import AddMonitor from './Pages/AddMonitor';
import Monitors from './Pages/Monitors'

function App() {
  return (

    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/add-monitor" element= {<AddMonitor/>} />
        <Route path="/monitors" element={<Monitors/>}/>
      </Routes>
    
  </Router>
  );
}

export default App;
