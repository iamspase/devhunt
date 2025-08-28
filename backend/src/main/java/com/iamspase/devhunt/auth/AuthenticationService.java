package com.iamspase.devhunt.auth;

import com.iamspase.devhunt.account.Account;
import com.iamspase.devhunt.account.AccountService;
import com.iamspase.devhunt.user.LoginRequest;
import com.iamspase.devhunt.user.RegisterRequest;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    public AuthenticationService(
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            AccountService accountService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User register(RegisterRequest responseData) {
        User user = new User();
        user.setEmail(responseData.getEmail());
        user.setPassword(passwordEncoder.encode(responseData.getPassword()));

        User savedUser = userRepository.save(user); // I need this, because user id is generated after save

        Account account = new Account();
        account.setUserId(savedUser.getId());
        account.setEmail(responseData.getEmail());
        account.setFirstName(responseData.getFirstName());
        account.setLastName(responseData.getLastName());
        account.setAge(responseData.getAge());
        account.setGender(responseData.getGender());
        account.setIndustry(responseData.getIndustry());

        accountService.createAccount(account, savedUser.getId());
        savedUser.setAccountId(account.getId());

        return userRepository.save(savedUser);
    }

    public User authenticate(LoginRequest responseData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(responseData.getEmail(), responseData.getPassword())
        );

        return userRepository.findByEmail(responseData.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(responseData.getEmail()));
    }

}
