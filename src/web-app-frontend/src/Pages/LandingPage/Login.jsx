import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";

import { loginUser } from "../../Services/login";
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
        <div style={{margin:"10px"}}>
          <CssTextField
            value={userName}
            name="loginEmailOrUsername"
            onChange={handleNameChange}
            id="standard-basic"
            label="Username"
            variant="standard"
          />
        </div>
        <div style={{margin:"10px"}}>
          <CssTextField
            value={password}
            name="loginPassword"
            onChange={handlePasswordChange}
            id="standard-basic"
            label="Password"
            variant="standard"
            type="password"
          />
        </div>
        <div style={{ margin:"10px"}}>
          <CssButton type="submit" style={{ color:"#04284a", width:"100%"}}>
            Log In
          </CssButton>
        </div>
      </form>

      <div style={{ margin: "10px" }}>
        <p>
          You don't have an account?{" "}
          <Button onClick={handleGoSignup} style={{color:"black"}} >Sign up</Button>{" "}
        </p>
      </div>
    </div>
  );
}

export default Login;
