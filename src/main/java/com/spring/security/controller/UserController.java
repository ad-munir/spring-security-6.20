package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

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

}
