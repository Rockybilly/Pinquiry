import React, { useState } from "react";
import "../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link } from "react-router-dom";
import Navbar from "../Components/Navbar";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";

export default class AddMonitor extends React.Component {
  constructor() {
    super();
    this.state = {
      age: 0,
      url: null,
      port: null,
      timeInterval: null,
      threshold: null,
      searchString: null,
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange = (event) => {
    this.setState({ age: event.target.value });
  };

  handleInputChange = (event) => {
    event.preventDefault();
    const target = event.target;
    this.setState({
      [target.name]: target.value,
    });
  };

  handleSubmit = (event) => {
    event.preventDefault();
    console.log(this.state);
  };

  render() {
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
            <form onSubmit={this.handleSubmit}>
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
                <InputLabel>Monitor Type</InputLabel>
                <Select
                  value={this.state.age}
                  label="Monitor Type"
                  onChange={this.handleChange}
                  defaultValue={0}
                >
                  <MenuItem value={0}>HTTP/HTTPS</MenuItem>
                  <MenuItem value={1}>TCP</MenuItem>
                  <MenuItem value={2}>Content</MenuItem>
                </Select>
                <TextField
                  onChange={this.handleInputChange}
                  name="url"
                  id="standard-basic"
                  label="URL"
                  variant="standard"
                />
                <TextField
                  onChange={this.handleInputChange}
                  name="port"
                  id="standard-basic"
                  label="Port"
                  variant="standard"
                />
                <TextField
                  onChange={this.handleInputChange}
                  name="timeInterval"
                  id="standard-basic"
                  label="Time Interval"
                  variant="standard"
                />
                <TextField
                  onChange={this.handleInputChange}
                  name="threshold"
                  id="standard-basic"
                  label="Timeout Threshold"
                  variant="standard"
                />
                <TextField
                  onChange={this.handleInputChange}
                  name="searchString"
                  id="standard-basic"
                  label="Search String"
                  variant="standard"
                />

                <Button type="submit" variant="outlined">
                  ADD
                </Button>
              </Grid>
            </form>
          </Grid>
        </Grid>
      </div>
    );
  }
}
