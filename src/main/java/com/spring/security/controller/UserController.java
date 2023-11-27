package com.spring.security.controller;

import com.spring.security.dto.auth.AuthRequest;
import com.spring.security.dto.auth.AuthResponse;
import com.spring.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home() {
        return "Home Page!!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin Page!!";
    }

    @GetMapping("/profile")
    public String profile() {
        return "Profile Page!!";
    }

    @GetMapping("/services")
    public String services() {
        return "Services Page!!";
    }


    @PostMapping("/register")
    private ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
