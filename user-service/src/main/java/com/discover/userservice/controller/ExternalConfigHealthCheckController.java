package com.discover.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/config")
public class ExternalConfigHealthCheckController {

    @Value("${user.role}") String role;

    @GetMapping(value="/{username}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getExternalConfig(@PathVariable("username") String username) {
        return String.format("Hello! You're %s and you'll become an %s...", username, role);
    }

}