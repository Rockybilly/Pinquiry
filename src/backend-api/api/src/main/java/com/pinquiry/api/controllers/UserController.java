package com.pinquiry.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinquiry.api.config.CustomAuthenticationManager;
import com.pinquiry.api.config.CustomUserDetailService;
import com.pinquiry.api.config.JwtTokenUtil;
import com.pinquiry.api.model.User;
import com.pinquiry.api.model.rest.request.TokenRequest;
import com.pinquiry.api.model.rest.response.TokenResponse;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CustomAuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CustomUserDetailService userDetailsService;


    private void authenticate(User u) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public String  createAuthenticationToken(User u) throws Exception {

        authenticate(u);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(u.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    @GetMapping("/showUsers")
    public ResponseEntity<List<User>> findUsers() {

        var cities = (List<User>) userService.findAll();
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        try {
            User u = userService.findUserByUsername(user.getUsername());
        }catch (Exception e) {


            String t = "";

            boolean succ = userService.createUser(user);
            if (succ) {
                try {
                    t = createAuthenticationToken(user);
                } catch (Exception e1) {
                    return ResponseEntity.status(409).body("Could not signup");
                }

                return ResponseEntity.status(201).body(t);
            } else
                return ResponseEntity.status(409).body("Could not signup");
        }
        return ResponseEntity.status(409).body("Username is already taken");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TokenRequest request) {
        User u;
        try {
            u = userService.findUserByUsername(request.getUsername());
        }catch (Exception e){
            return ResponseEntity.status(401).body("User not found");
        }

        String t;

        try {
            t = createAuthenticationToken(u);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Wrong password");
        }

        return ResponseEntity.status(200).body(t);

    }



    @PostMapping("/delete-user")
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

        if(!Objects.equals(rootNode.path("current_password").asText(), userService.findUserById(rootNode.path("id").asInt()).getPassword())){
            return ResponseEntity.status(409).body("Current passwords not matching");
        }
        else{
            userService.updatePassword(userService.findUserById(rootNode.path("id").asInt()),  rootNode.path("new_password").asText());
        }
        return ResponseEntity.status(200).body(null);
    }
}
