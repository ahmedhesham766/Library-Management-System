package com.example.LibraryManagementSystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationRequestDTO {
    private String username;
    private String password;

}
