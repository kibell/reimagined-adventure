// This configuration class sets up security configurations for the application, including login, logout, and access control.

package com.example.flightapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
              // Allow access to login, register, and static resources (CSS, JS)
                .antMatchers("/login", "/register", "/css/**", "/js/**").permitAll() 
                // Restrict access to authenticated users
                .anyRequest().authenticated() 
                .and()
            .formLogin()
            // Custom login page
                .loginPage("/login")
                // Redirect to index.html on successful login
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
// Defines a bean for password encoding using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}