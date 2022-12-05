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
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
                enim ad minim veniam, quis nostrud exercitation ullamco laboris
                nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor
                in reprehenderit in voluptate velit esse cillum dolore eu fugiat
                nulla pariatur. Excepteur sint occaecat cupidatat non proident,
                sunt in culpa qui officia deserunt mollit anim id est laborum."{" "}
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
