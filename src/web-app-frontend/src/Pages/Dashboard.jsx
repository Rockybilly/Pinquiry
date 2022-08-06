import React, { useState } from 'react';
import '../App.css';

import { AppBar, Button, Typography } from '@mui/material';
import Grid from '@mui/material/Grid';
import TextField, { textFieldClasses } from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Navbar from '../Components/Navbar';



class Dashboard extends React.Component {
  render() {
    return (
      <div style={{ width: "100%", height: "100%", margin: "0px", padding: "0px" }}>
        <Navbar />
        <Grid
          container
          direction="row"
          alignItems="stretch"
        >
          <Grid item
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
            lg={12} style={{ margin: "0px", padding: "20px", background: "#D8E9A8", height: "100vh", color: "#1E5128" }} >


            <Button variant="outlined"><Link to="/add-monitor"> Add Monitor</Link></Button>
          </Grid>



        </Grid>
      </div>
    );
  }
}

export default Dashboard;
