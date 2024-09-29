// This configuration class sets up beans for the application, including RestTemplate and Duffel access token.
// Spring documentaion for bean defs https://docs.spring.io/spring-framework/reference/core/beans/introduction.html#:~:text=In%20Spring%2C%20the%20objects%20that,many%20objects%20in%20your%20application.

package com.example.flightapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {
    // Injects the value of 'duffel.accessToken' from application properties
    @Value("${duffel.accessToken}")
    private String duffelAccessToken;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String duffelAccessToken() {
        // Returns the Duffel access token
        return duffelAccessToken;
    }
}