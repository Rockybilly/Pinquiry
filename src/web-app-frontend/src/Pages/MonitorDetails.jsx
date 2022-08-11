import React, { useState } from "react";
import "../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link } from "react-router-dom";
import Navbar from "../Components/Navbar";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";

class Dashboard extends React.Component {
  render() {
    return (
      <div
        style={{ width: "100%", height: "100%", margin: "0px", padding: "0px" }}
      >
        <Grid container direction="row" alignItems="stretch">
          <Grid
            item
            container
            direction="column"
            justifyContent="center"
            alignItems="flex-start"
            lg={12}
            style={{
              margin: "0px",
              padding: "20px",
              background: "#D8E9A8",
              height: "100vh",
              color: "#1E5128",
            }}
          >
            <Box sx={{ flexGrow: 1, maxWidth: 752 }}>
              <Grid spacing={0}>
                <Grid item>
                  <Typography>Text only</Typography>

                  <List>
                    <ListItem>
                      <ListItemText primary="Monitor #1" />
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Monitor #2" />
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Monitor #3" />
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Monitor #4" />
                    </ListItem>
                  </List>
                </Grid>
              </Grid>
            </Box>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default Dashboard;
