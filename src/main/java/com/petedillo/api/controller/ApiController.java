package com.petedillo.api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ApiController {

    @GetMapping("posts")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

}
