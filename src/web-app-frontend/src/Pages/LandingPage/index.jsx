import React, { useEffect, useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link, Navigate } from "react-router-dom";

import Signup from "./Signup";
import Login from "./Login";

import { Spin } from "antd";
import { useStoreState } from "easy-peasy";

function LandingPage() {
  const [login, setLogin] = useState(true);
  const loginLoading = useStoreState((state) => state.loginLoading);

  const user = useStoreState((state) => state.user);

  if (!loginLoading && user.isAuthenticated) {
    return <Navigate to="/dashboard" />;
  }

  /*
    useEffect(() => {
      console.log(loginLoading);
    }, [loginLoading]);*/

  return (
    <Spin spinning={loginLoading}>
      <div
        style={{
          width: "100%",
          height: "100vh",
          margin: "0px",
          padding: "0px",
        }}
      >
        <Grid container direction="row" alignItems="stretch">
          <Grid
            item
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
            lg={7}
            style={{ margin: "0px", padding: "20px", background: "#191A19" }}
          >
            <div>
              {!loginLoading && (
                <h1 style={{ color: "#79DAE8" }}> Pinquiry </h1>
              )}

              <p style={{ color: "#C1FFD7" }}>
                Monitor your website
                <br />
                <br />
                Pinquiry is a web page monitoring service that notifies website
                owners about incidents, allows them to monitor their sites in
                real time, and provides detailed incident reports.
                <br />
                <br />
                Features section:
                <br />
                <br />
                Alerted when something is wrong
                <br />
                <br />
                Pinquiry alerts you when your site or server has gone down. It
                monitors the availability of the servers and the responses of
                the website in near real time, and notifies you if there's an
                incident.
                <br />
                <br />
                Monitoring done right
                <br />
                <br />
                Pinquiry is a web page monitoring service that helps ensure your
                site is up, running, and responsive at all times. It monitors
                the availability of the servers and the responses of the website
                in near real time, and notifies you if there's an incident.
                <br />
                <br />
                Accurate monitoring with Pinquiry
                <br />
                <br />
                The website monitoring tool tracks every single data point
                related to your site, like uptime percentage, response time,
                bandwidth consumption etc. It also provides detailed reports on
                incidents that have happened over a period of time (e.g., last 3
                months).
                <br />
                <br />{" "}
              </p>
            </div>
          </Grid>
          <Grid
            item
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
            lg={5}
            style={{ margin: "0px", background: "#79DAE8", height: "100vh" }}
          >
            {login ? (
              <Login onClickSignup={() => setLogin(false)} />
            ) : (
              <Signup onGoLogin={() => setLogin(true)} />
            )}
          </Grid>
        </Grid>
      </div>
    </Spin>
  );
}

export default LandingPage;
