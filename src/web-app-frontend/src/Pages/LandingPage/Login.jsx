import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";

import { loginUser } from "../../Services/login";
import { useStoreActions } from "easy-peasy";

function Login({ onClickSignup }) {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const doLogin = useStoreActions((actions) => actions.doLogin);
  const setLoginLoading = useStoreActions((actions) => actions.setLoginLoading);

  const handleNameChange = (event) => {
    event.preventDefault();
    setUserName(event.target.value);
  };

  const handlePasswordChange = (event) => {
    event.preventDefault();
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    doLogin({ name: userName, pass: password });
  };

  const handleGoSignup = () => {
    onClickSignup();
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <TextField
            value={userName}
            name="loginEmailOrUsername"
            onChange={handleNameChange}
            id="standard-basic"
            label="Username"
            variant="standard"
          />
        </div>

        <div>
          <TextField
            value={password}
            name="loginPassword"
            onChange={handlePasswordChange}
            id="standard-basic"
            label="Password"
            variant="standard"
            type="password"
          />
        </div>
        <div style={{ textAlign: "right", paddingRight: "27%" }}>
          <button type="submit" style={{ margin: "7px" }} variant="outlined">
            Log In
          </button>
        </div>
      </form>

      <div style={{ margin: "10px" }}>
        <p>
          You don't have an account?{" "}
          <Button onClick={handleGoSignup}>Sign up</Button>{" "}
        </p>
      </div>
    </div>
  );
}

export default Login;
