package com.example.flightapi.controller;

import com.example.flightapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/searchFlights")
    public String searchFlights(@RequestParam String origin, @RequestParam String destination, @RequestParam String departureDate) {
        return userService.searchFlights(origin, destination, departureDate);
    }
}