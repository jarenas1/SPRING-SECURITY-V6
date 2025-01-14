package com.security.security.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@PreAuthorize("denyAll()")
public class TestAuthController {

    @GetMapping("/index")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/index2")
    public String index2() {
        return "Hello World wo security";
    }
}
