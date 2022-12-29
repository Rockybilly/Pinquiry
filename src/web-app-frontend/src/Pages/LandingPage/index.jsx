import React, { useEffect, useState } from "react";

import logoRzgi from "../../static/PinquiryLogoRzgi.png";
import "../../App.css";
import { AppBar, Button, Typography, Paper } from "@mui/material";
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
            style={{ margin: "0px", padding: "20px", background: "#001529" }}
          >
            <div>
              {!loginLoading && (
                <h5 style={{ color: "#79DAE8" }}> <img
                style={{
                  aspectRatio: "795/277",
                  marginLeft: 8,
                }}
                src={logoRzgi}
                alt="Logo"
              /></h5>
              )}
              <Paper elevation={11} style={{ margin: "0px", padding: "20px", background: "#001529" }} square>

              <div style={{ color: "#f1f1f1" }}>
                <Typography style={{ color: "#f1f1f1" , fontFamily: "'Teko', sans-serif"}} variant="h3"> Monitor your website </Typography>
                <Typography style={{ color: "#f1f1f1", fontFamily: "'Asap', sans-serif" , fontSize:"14pt"}} variant="subtitle1" paragraph="true">
                Pinquiry is a web page monitoring service that notifies website
                owners about incidents, allows them to monitor their sites in
                real time, and provides detailed incident reports.
                </Typography>
                <br />
                <br />
                <Typography style={{ color: "#f1f1f1", fontFamily: "'Teko', sans-serif"}} variant="h4"> What we offer: </Typography>
                <ul>
                  <li> <Typography  style={{ color: "#f1f1f1", fontFamily: "'Asap', sans-serif"}}  variant="subtitle2" paragraph="true" gutterBottom> Be on alert when something is wrong: <br/>
                  Pinquiry alerts you when your site or server has gone down. It
                monitors the availability of the servers and the responses of
                the website in near real time, and notifies you if there's an
                incident.</Typography> </li>
                
                <li><Typography  style={{ color: "#f1f1f1", fontFamily: "'Asap', sans-serif"}} variant="subtitle2" paragraph="true" gutterBottom>Monitoring done right:<br/>
                Pinquiry is a web page monitoring service that helps ensure your
                site is up, running, and responsive at all times. It monitors
                the availability of the servers and the responses of the website
                in near real time, and notifies you if there's an incident.</Typography></li>

                <li><Typography  style={{ color: "#f1f1f1", fontFamily: "'Asap', sans-serif"}}  variant="subtitle2" paragraph="true" gutterBottom>Accurate monitoring with Pinquiry:<br/>
                The website monitoring tool tracks every single data point
                related to your site, like uptime percentage, response time,
                bandwidth consumption etc. It also provides detailed reports on
                incidents that have happened over a period of time (e.g., last 3
                months). </Typography></li>

                </ul>
                <br />{" "}
              </div>
              </Paper>
            </div>
          </Grid>
          <Grid
            item
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
            lg={5}
            style={{ margin: "0px", background: "#4580a1", height: "100vh" }}
          >
            <Typography style={{ color: "#001529", marginBottom:"10%", fontFamily: "'Teko', sans-serif" }} variant="h3"> ▞  Welcome to Pinquiry  ▚   </Typography>
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
