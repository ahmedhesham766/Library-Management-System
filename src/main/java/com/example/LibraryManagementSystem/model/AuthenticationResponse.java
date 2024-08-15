package com.example.LibraryManagementSystem.model;

import lombok.Getter;

@Getter
public record AuthenticationResponse(String jwt) { }
