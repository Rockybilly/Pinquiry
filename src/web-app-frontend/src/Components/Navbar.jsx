import React from "react";

import { AppBar, Button, Typography, Toolbar } from "@mui/material";
import { Link } from "react-router-dom";

function Navbar() {
  return (
    <div>
      <AppBar
        position="static"
        style={{ background: "#191A19", borderWidth: "6px", color: "#79DAE8" }}
      >
        <Toolbar variant="dense">
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Pinquiry
          </Typography>
          <Link style={{ textDecoration: "none" }} to="/about">
            <Button style={{ color: "#fff" }}>About</Button>
          </Link>
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default Navbar;
