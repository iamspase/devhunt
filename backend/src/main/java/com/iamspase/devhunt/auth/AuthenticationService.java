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

    public User register(RegisterRequest responseData) {
        User user = new User();
        user.setEmail(responseData.getEmail());
        user.setPassword(passwordEncoder.encode(responseData.getPassword()));

        Account account = new Account();
        account.setUser(user);
        account.setEmail(responseData.getEmail());
        account.setFirstName(responseData.getFirstName());
        account.setLastName(responseData.getLastName());
        account.setAge(responseData.getAge());
        account.setGender(responseData.getGender());
        account.setIndustry(responseData.getIndustry());

        userRepository.save(user);
        accountService.createAccount(account, user.getId());

        return user;
    }

    public User authenticate(LoginRequest responseData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(responseData.getEmail(), responseData.getPassword())
        );

        return userRepository.findByEmail(responseData.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(responseData.getEmail()));
    }

}
