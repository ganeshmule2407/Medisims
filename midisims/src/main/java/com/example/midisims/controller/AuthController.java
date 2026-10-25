package com.example.midisims.controller;

import com.example.midisims.dto.LoginPharmacyRequest;
import com.example.midisims.dto.RegisterPharmacyRequest;
import com.example.midisims.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    AuthService service;
    @PostMapping("/register")
    public String register(@RequestBody RegisterPharmacyRequest request){
        return service.register(request);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginPharmacyRequest request){return service.login(request);}
    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }
}
