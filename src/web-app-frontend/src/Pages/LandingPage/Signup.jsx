import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link } from "react-router-dom";
import { useStoreActions } from "easy-peasy";
import { alpha, styled } from '@mui/material/styles';

const md5 = require("md5");

const CssTextField = styled(TextField)({
  '& label.Mui-focused': {
    color: '#001529',
  },
  '& .MuiInput-underline:after': {
    borderBottomColor: '#001529',
  },
  '& .MuiOutlinedInput-root': {
    '& fieldset': {
      borderColor: 'red',
    },
    '&:hover fieldset': {
      borderColor: 'yellow',
    },
    '&.Mui-focused fieldset': {
      borderColor: 'green',
    },
  },
});


const CssButton = styled(Button)({
    '&:hover': {
      color: '#04284a',
  }
});

function Signup({ onGoLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmationPassword, setConfirmationPassword] = useState("");
  const [error, setError] = useState(null);
  const doSingup = useStoreActions((actions) => actions.doSignup);

  const handleNameChange = (event) => {
    event.preventDefault();
    setUsername(event.target.value);
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
    } else {
      doSingup({ username, password });
    }

    console.log(event);
  };

  const handleGoLogin = () => {
    onGoLogin();
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div style={{margin:"10px"}}>
          <CssTextField
            name="signupName"
            onChange={handleNameChange}
            id="standard-basic"
            label="Name"
            variant="standard"
            style={{ color: "#4E9F3D", width:"100%" }}
          />
        </div>

        <div style={{margin:"10px"}}>
          <CssTextField
            name="password"
            onChange={handlePasswordChange}
            id="standard-basic"
            label="Password"
            variant="standard"
            type="password"
            style={{ color: "#4E9F3D", width:"100%" }}
          />
        </div>
        <div style={{margin:"10px"}}>
          <CssTextField
            name="confirmationPassword"
            onChange={handleConfirmationPasswordChange}
            id="standard-basic"
            label="Password Again"
            variant="standard"
            type="password"
            style={{ color: "#4E9F3D", width:"100%" }}
          />
        </div>
        {error && <p>{error}</p>}

        <div style={{ margin:"10px"}}>
          <CssButton type="submit" style={{ color:"#001529", width:"100%"}}>
            Sign Up
          </CssButton>
        </div>
      </form>

      <div style={{ margin: "10px" }}>
        <p>
          Do you already have an account?{" "}
          <Button onClick={handleGoLogin} style={{color:"black"}} >Login</Button>{" "}
        </p>
      </div>
    </div>
  );
}

export default Signup;
