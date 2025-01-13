package com.security.security.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/index2")
    public String index2() {
        return "Hello World wo security";
    }
}
