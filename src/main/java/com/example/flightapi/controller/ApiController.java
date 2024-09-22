package com.example.flightapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

import com.example.flightapi.ApiService;

@Controller
public class ApiController {

    private ApiService apiService;

    @GetMapping("/fetch-data")
    public String fetchData(
            @RequestParam String departureAirportCode,
            @RequestParam String arrivalAirportCode,
            @RequestParam String departureDate) {
        apiService.callExternalApi(departureAirportCode, arrivalAirportCode, departureDate);
        return "index"; // This will look for src/main/resources/templates/index.html
    }
}