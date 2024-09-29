// This file contains the controller class for the user. It has a method to search for flights.
package com.example.flightapi.controller;

import com.example.flightapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// Maps the controller to the "/api" URL
@RequestMapping("/api")
public class UserController {

// Injects the UserService bean
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Handles GET requests to "/api/searchFlights"
    @GetMapping("/searchFlights")
    // Maps the request parameters to method parameters
    public String searchFlights(@RequestParam String origin, @RequestParam String destination, @RequestParam String departureDate) {
        return userService.searchFlights(origin, destination, departureDate);
    }
}