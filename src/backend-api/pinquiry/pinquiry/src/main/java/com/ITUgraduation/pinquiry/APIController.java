package com.ITUgraduation.pinquiry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @CrossOrigin(origins= "*")
    @GetMapping("/hello")
    ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");

    }
}
