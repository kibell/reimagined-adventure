package com.example.flightapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.flightapi.model.User;
import com.example.flightapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final XMLUserService xmlUserService;

    @Autowired
    public UserService(UserRepository userRepository, XMLUserService xmlUserService) {
        this.userRepository = userRepository;
        this.xmlUserService = xmlUserService;
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
}