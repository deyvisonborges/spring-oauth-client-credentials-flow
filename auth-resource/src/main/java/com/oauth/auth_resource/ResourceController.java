package com.oauth.auth_resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping
    public String getData() {
        return "This is protected data";
    }
}