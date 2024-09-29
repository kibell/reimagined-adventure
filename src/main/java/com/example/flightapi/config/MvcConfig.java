// This configuration class sets up view controllers for the application.

package com.example.flightapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override // Overrides the addViewControllers method from WebMvcConfigurer
    public void addViewControllers(ViewControllerRegistry registry) {
        // Maps the root URL ("/") to the "index" view
        registry.addViewController("/").setViewName("index");
        // Maps the "/login" URL to the "login" view
        registry.addViewController("/login").setViewName("login");
        // Maps the "/register" URL to the "register" view
        registry.addViewController("/register").setViewName("register");
    }
}