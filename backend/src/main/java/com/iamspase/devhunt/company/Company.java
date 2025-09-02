package com.iamspase.devhunt.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String headline;
    private String about;
    private String website;
    private String country;
    private String city;
    private String address;
    private String industry;

    @Column(name = "employee_count")
    private int employeeCount;

    private Long userId;

}
