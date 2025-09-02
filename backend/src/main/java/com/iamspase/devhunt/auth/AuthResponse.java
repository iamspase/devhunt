package com.iamspase.devhunt.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private long expiresIn;
}
