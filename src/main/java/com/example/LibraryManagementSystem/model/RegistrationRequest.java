package com.example.LibraryManagementSystem.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistrationRequest {

    private String username;
    private String password;
    private String roles;

}