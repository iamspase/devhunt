package com.iamspase.devhunt.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyRegistrationRequest {
    private String email;
    private String password;
    private String name;
    private String headline;
    private String about;
    private String website;
    private String country;
    private String city;
    private String address;
    private String industry;
    private int employeeCount;
}
