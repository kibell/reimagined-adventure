// This controller handles authentication-related requests such as login and registration.

package com.example.flightapi.controller;

import com.example.flightapi.model.User;
import com.example.flightapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        // Initializing userService and passwordEncoder
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Handles GET requests to "/login"
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        //login.html
        return "login";
    }

    // Handles GET requests to "/register"
    @GetMapping("/register")
    public String register() {
        //register.html
        return "register";
    }

    // Handles POST requests to "/register"
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User();
        // Sets the username and password for the user
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // Registers the user
        userService.register(user);
        // Redirects to the login page
        return "redirect:/login";
    }
}