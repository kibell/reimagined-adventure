package com.example.flightapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.example.flightapi.model.User;
import com.example.flightapi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final XMLUserService xmlUserService;
    private final RestTemplate restTemplate;
    private final String duffelAccessToken;

    public UserService(UserRepository userRepository, XMLUserService xmlUserService,
                       @Value("${duffel.accessToken}") String duffelAccessToken) {
        this.userRepository = userRepository;
        this.xmlUserService = xmlUserService;
        this.restTemplate = new RestTemplate();
        this.duffelAccessToken = duffelAccessToken;
    }

    public void register(User user) {
        userRepository.save(user);
        xmlUserService.register(user.getUsername(), user.getPassword());
    }

    public boolean authenticate(String username, String password) {
        boolean dbAuth = userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
        boolean xmlAuth = xmlUserService.authenticate(username, password);
        return dbAuth || xmlAuth;
    }

    public String searchFlights(String origin, String destination, String departureDate) {
        String apiUrl = "https://api.duffel.com/air/offer_requests";
        String requestBody = String.format(
            "{\"data\":{\"slices\":[{\"origin\":\"%s\",\"destination\":\"%s\",\"departure_date\":\"%s\"}],\"passengers\":[{\"type\":\"adult\"}]}}",
            origin, destination, departureDate
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + duffelAccessToken);
        headers.set("Content-Type", "application/json");
        headers.set("Duffel-Version", "v1"); // Add the Duffel-Version header

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}