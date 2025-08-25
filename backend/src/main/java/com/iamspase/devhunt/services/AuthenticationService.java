package com.iamspase.devhunt.services;

import com.iamspase.devhunt.user.LoginResponseDto;
import com.iamspase.devhunt.user.RegisterResponseDto;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserRepository userRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User register(RegisterResponseDto responseData) {
        User user = new User();
        user.setEmail(responseData.getEmail());
        user.setPassword(passwordEncoder.encode(responseData.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginResponseDto responseData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(responseData.getEmail(), responseData.getPassword())
        );

        return userRepository.findByEmail(responseData.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(responseData.getEmail()));
    }

}
