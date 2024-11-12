package com.realtimechat.realtimechatapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> homePage(){
        return new ResponseEntity<String>("Welcome to Home Page", HttpStatus.OK);
    }
}
