import React, {useState} from 'react';
import '../App.css';

import { AppBar, Button, Typography } from '@mui/material';
import Grid from '@mui/material/Grid';
import TextField, { textFieldClasses } from '@mui/material/TextField';
import {Link} from 'react-router-dom';
import Navbar from '../Components/Navbar';



class LandingPage extends React.Component {
    constructor() {
        super();
        this.state = {
            login: true,
            loginEmailOrUsername: "",
            loginPassword: "",
            signupEmail:"",
            signupPassword1:"",
            signupPassword2:""
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleLoginSubmit = this.handleLoginSubmit.bind(this);
        this.handleSignUpSubmit = this.handleSignUpSubmit.bind(this);
      }

    handleInputChange(event) {
    event.preventDefault();
    const target = event.target;
    this.setState({
        [target.name]: target.value,
    });
    }

    handleLoginSubmit(event) {
        event.preventDefault();
        console.log("username " + this.state.loginEmailOrUsername)
        console.log("password " + this.state.loginPassword)
    }

    async handleSignUpSubmit(event){

        if(this.state.signupPassword2 != this.state.signupPassword1){
            event.preventDefault();
            alert("Passwords do not match")
        }

        

        
        else{
        event.preventDefault();
        console.log("username " + this.state.signupEmail)
        console.log("password1 " + this.state.signupPassword1)
        console.log("password2 " + this.state.signupPassword2)
        }
    }

    


render(){
  return (
    <div style={{ width:"100%", height:"100%", margin:"0px", padding:"0px" }}>
        <Navbar/>
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
          lg={7} style={{margin:"0px", padding:"20px", background:"#191A19"} } >
            
              <div>
                <h1 style={{color:"#79DAE8"}}> Pinquiry </h1>
                <p style={{color:"#C1FFD7"}} >
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."  </p>
              </div>
          </Grid>
          <Grid item 
               
                container
                direction="column"
                justifyContent="center"
                alignItems="center"
              
                lg={5} style={{margin:"0px", background:"#79DAE8", height:"100vh"}}

                > 
                 <Link to="/dashboard">Dashboard</Link>


                {this.state.login && 
                    <div>
                    <form onSubmit={this.handleLoginSubmit}>
                        <div>
                        <TextField name='loginEmailOrUsername' onChange={this.handleInputChange} id="standard-basic" label="Username" variant="standard" />
                        </div>

                        <div>
                        <TextField name='loginPassword' onChange={this.handleInputChange}  id="standard-basic" label="Password" variant="standard" type="password" />
                        </div> 
                        <div style={{textAlign:"right", paddingRight:"27%"}}>

                    

                            <button type="submit" style={{margin:"7px"}} variant="outlined">Log In</button>
                        </div>
                    </form>

                    
                    <div style={{margin:"10px"}}>
                   
                            <p>You don't have an account  <Button
                        onClick={() => {
                            this.setState({login:false})
                        }}
                        >
                        Sign up
                        </Button> </p>
                    </div>
                </div>
                
                }

                {!this.state.login &&
                    <div>
                        <form onSubmit={this.handleSignUpSubmit} >
                            <div>
                            <TextField name="signupEmail" onChange={this.handleInputChange} id="standard-basic" label="Email" variant="standard" style={{color:"#4E9F3D"}} />
                            </div>

                            <div>
                            <TextField name="signupPassword1" onChange={this.handleInputChange} id="standard-basic" label="Password" variant="standard" type="password" />
                            </div> 
                            <div>
                            <TextField name="signupPassword2"  onChange={this.handleInputChange} id="standard-basic" label="Password Again" variant="standard" type="password" />
                            </div> 


                            <div style={{textAlign:"right", paddingRight:"27%"}}>

                        

                            <Button type="submit" variant="outlined">Sign Up</Button>
                            </div> 
                        </form>

                        
                        <div style={{margin:"10px"}}>
                    
                                <p>Do you already have an account?   <Button
                            onClick={() => {
                                this.setState({login:true})
                            }}
                            >
                            Log In
                            </Button> </p>
                        </div>
                    </div>
                }
                


                 

                  
               
              
              
          </Grid>
      </Grid>
    </div>
  );
            }
}

export default LandingPage;
