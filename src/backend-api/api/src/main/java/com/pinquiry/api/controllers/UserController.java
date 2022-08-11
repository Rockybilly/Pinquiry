package com.pinquiry.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinquiry.api.model.User;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/showUsers")
    public ResponseEntity<List<User>> findUsers() {

        var cities = (List<User>) userService.findAll();
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        System.out.println(user.getPassword());

        boolean succ = userService.createUser(user);
        if (succ )
            return ResponseEntity.status(201).body("Created");
        else
            return ResponseEntity.status(409).body("Not created");

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
