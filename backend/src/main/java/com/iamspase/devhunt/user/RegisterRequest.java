package com.iamspase.devhunt.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private int age;
    private String industry;
}
