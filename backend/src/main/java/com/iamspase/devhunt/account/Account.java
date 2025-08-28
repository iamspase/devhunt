package com.iamspase.devhunt.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iamspase.devhunt.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = true, name = "photo_url")
    private String photoUrl;
    @Column(nullable = true, name = "banner_img_url")
    private String bannerImgUrl;
    private String country;
    private String city;
    @Column(nullable = true, name = "phone_number")
    private String phoneNumber;
    private String headline;
    private String about;
    private String education;

    private Long userId;
}
