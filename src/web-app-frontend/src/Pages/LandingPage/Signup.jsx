import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link } from "react-router-dom";
import Navbar from "../../Components/Navbar";

function Signup({ onGoLogin }) {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmationPassword, setConfirmationPassword] = useState("");
  const [error, setError] = useState(null);

  const handleNameChange = (event) => {
    event.preventDefault();
    setUserName(event.target.value);
  };

  const handlePasswordChange = (event) => {
    event.preventDefault();
    setPassword(event.target.value);
    setError(null);
  };

  const handleConfirmationPasswordChange = (event) => {
    event.preventDefault();
    setConfirmationPassword(event.target.value);
    setError(null);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (password !== confirmationPassword) {
      setError("Passwords do not match");
    }

    console.log(event);
  };

  const handleGoLogin = () => {
    onGoLogin();
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <TextField
            name="signupName"
            onChange={handleNameChange}
            id="standard-basic"
            label="Name"
            variant="standard"
            style={{ color: "#4E9F3D" }}
          />
        </div>

        <div>
          <TextField
            name="password"
            onChange={handlePasswordChange}
            id="standard-basic"
            label="Password"
            variant="standard"
            type="password"
          />
        </div>
        <div>
          <TextField
            name="confirmationPassword"
            onChange={handleConfirmationPasswordChange}
            id="standard-basic"
            label="Password Again"
            variant="standard"
            type="password"
          />
        </div>
        {error && <p>{error}</p>}

        <div style={{ textAlign: "right", paddingRight: "27%" }}>
          <Button type="submit" variant="outlined">
            Sign Up
          </Button>
        </div>
      </form>

      <div style={{ margin: "10px" }}>
        <p>
          Do you already have an account?{" "}
          <Button onClick={handleGoLogin}>Log In</Button>{" "}
        </p>
      </div>
    </div>
  );
}

export default Signup;
