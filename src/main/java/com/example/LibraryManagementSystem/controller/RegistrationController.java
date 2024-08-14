package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.model.RegistrationRequest;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.service.Impl.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationRequest request) {
        return registrationService.registerUser(request.getUsername(), request.getPassword());
    }
}