package com.pinquiry.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {



    @GetMapping("/control")
    public ResponseEntity<String> c() {

        return ResponseEntity.status(200).body( "Hello, ");
    }

    @GetMapping("/hello")
    public ResponseEntity<String>  hello(Authentication authentication) {

        return ResponseEntity.status(200).body( "Hello, " + authentication.getName() + "!");
    }





}