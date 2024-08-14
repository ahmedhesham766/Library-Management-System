package com.example.LibraryManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RegistrationRequest {

    private String username;
    private String password;
    private String roles;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String password, String roles, String username) {
        this.password = password;
        this.roles = roles;
        this.username = username;
    }

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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }


}