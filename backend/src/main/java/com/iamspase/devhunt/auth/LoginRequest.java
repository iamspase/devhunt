package com.iamspase.devhunt.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
// POJO for login request
public class LoginRequest {
    private String email;
    private String password;
}
