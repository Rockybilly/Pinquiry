package com.pinquiry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinquiry.api.config.CustomAuthenticationManager;
import com.pinquiry.api.config.CustomUserDetailService;
import com.pinquiry.api.config.JwtTokenUtil;
import com.pinquiry.api.model.TokenRequest;
import com.pinquiry.api.model.TokenResponse;
import com.pinquiry.api.model.User;
import com.pinquiry.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@Controller
public class MyController {


    @Autowired
    IUserService userService;

    @Autowired
    CustomUserDetailService userDetailsService;

    @Autowired
    CustomAuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @PostMapping( "/a")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new TokenResponse(token));
    }




    @GetMapping("/control")
    public ResponseEntity<String> c() {

        return ResponseEntity.status(200).body( "Hello, ");
    }

    @GetMapping("/hello")
    public ResponseEntity<String>  hello(Authentication authentication) {

        return ResponseEntity.status(200).body( "Hello, " + authentication.getName() + "!");
    }



    @GetMapping("/showUsers")
    public ResponseEntity<List<User>> findUsers() {

        var cities = (List<User>) userService.findAll();
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        boolean succ = userService.createUser(user);
        if (succ )
            return ResponseEntity.status(201).body("Created");
        else
            return ResponseEntity.status(409).body("Not created");

    }

    @PostMapping("/users/delete-user")
    public ResponseEntity<String> removeUser(@RequestBody long id) {
        boolean succ = userService.deleteUser(id);
        if (succ)
            return ResponseEntity.status(200).body("Deleted");
        else
            return ResponseEntity.status(409).body("Not deleted");

    }

    @PostMapping("/users/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(!Objects.equals(rootNode.path("current_password").asText(), userService.findUserById(rootNode.path("id").asInt()).getUser_password())){
            return ResponseEntity.status(409).body("Current passwords not matching");
        }
        else{
            userService.updatePassword(userService.findUserById(rootNode.path("id").asInt()),  rootNode.path("new_password").asText());
        }
        return ResponseEntity.status(200).body(null);
    }

}