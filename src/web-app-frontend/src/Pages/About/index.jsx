import React from "react";

import { AppBar, Button, Typography, Toolbar, Paper } from "@mui/material";
import { Link } from "react-router-dom";
import Navbar from "../../Components/Navbar";
import Grid from "@mui/material/Grid";

function About() {
  return (
    <div
      style={{ width: "100%", height: "100vh", margin: "0px", padding: "0px" }}
    >
      <Navbar />
      <Grid
        container
        direction="row"
        alignItems="stretch"
        style={{
          margin: "0px",
          padding: "20px",
          background: "#eceff1",
          height: "100%",
        }}
      >
        <Paper elevation={10} style={{ width: "100%" }}>
          <div
            style={{
              padding: "20px",
            }}
          >
            Lorem ipsum dolor sit amet
          </div>
        </Paper>
      </Grid>
    </div>
  );
}

export default About;
