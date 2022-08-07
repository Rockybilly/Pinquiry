import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link } from "react-router-dom";
import Navbar from "../../Components/Navbar";
import Signup from "./Signup";
import Login from "./Login";
import Loading from "../../Components/Loading";

function LandingPage() {
  const [login, setLogin] = useState(true);

  return (
    <div
      style={{ width: "100%", height: "100%", margin: "0px", padding: "0px" }}
    >
      <Navbar />
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
            <h1 style={{ color: "#79DAE8" }}> Pinquiry </h1>

            <p style={{ color: "#C1FFD7" }}>
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
              reprehenderit in voluptate velit esse cillum dolore eu fugiat
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
          <Link to="/dashboard">Dashboard</Link>

          {login ? (
            <Login onClickSignup={() => setLogin(false)} />
          ) : (
            <Signup onGoLogin={() => setLogin(true)} />
          )}
        </Grid>
      </Grid>
    </div>
  );
}

export default LandingPage;
