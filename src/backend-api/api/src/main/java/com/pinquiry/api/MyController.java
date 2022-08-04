package com.pinquiry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinquiry.api.model.User;
import com.pinquiry.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class MyController {

    @Autowired
    JwtEncoder encoder;
    @Autowired
    IUserService userService;

    @PostMapping("/token")
    public ResponseEntity<String> token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        // @formatter:on

        return ResponseEntity.status(201).body(encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
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
        JsonNode rootNode = null;
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