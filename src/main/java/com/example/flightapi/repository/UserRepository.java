package com.example.flightapi.repository;

import com.example.flightapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    // List to store User objects
    private List<User> users = new ArrayList<>();

    // Saves a user to the list
    public void save(User user) {
        // Adds the user to the list
        users.add(user);
    }

      // Finds a user by username
    public Optional<User> findByUsername(String username) {
        // Filters the list to find the first user with the given username
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}