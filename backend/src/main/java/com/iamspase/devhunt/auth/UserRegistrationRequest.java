package com.iamspase.devhunt.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
// This POJO is used to capture user registration details
public class UserRegistrationRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String industry;
}
