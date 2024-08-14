package com.example.LibraryManagementSystem.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
}