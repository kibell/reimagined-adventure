// this class represents a user  model 
package com.example.flightapi.model;

import javax.xml.bind.annotation.XmlRootElement;

// Indicates that this class can be converted to XML
@XmlRootElement
public class User {
    private String username;
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}