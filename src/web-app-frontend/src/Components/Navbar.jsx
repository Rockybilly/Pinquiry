import React from "react";

import { AppBar, Button, Typography } from '@mui/material';



function Navbar() {

  return (
    <div>
        <AppBar style={{ background:"#191A19", borderWidth:"6px", color:"#79DAE8" }}>
        <h3>Pinquiry</h3>
        </AppBar>
    </div>
  );
}

export default Navbar;
