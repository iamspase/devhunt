package com.iamspase.devhunt.auth;

import com.iamspase.devhunt.account.Account;
import com.iamspase.devhunt.account.AccountService;
import com.iamspase.devhunt.company.Company;
import com.iamspase.devhunt.company.CompanyService;
import com.iamspase.devhunt.user.Role;
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
    private final CompanyService companyService;

    public AuthenticationService(
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            AccountService accountService,
            CompanyService companyService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.companyService = companyService;
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User register(UserRegistrationRequest responseData) {
        User user = new User();
        user.setEmail(responseData.getEmail());
        user.setPassword(passwordEncoder.encode(responseData.getPassword()));
        user.setRole(Role.REGULAR);

        User savedUser = userRepository.save(user); // I need this, because user id is generated after save

        Account account = new Account();
        account.setUserId(savedUser.getId());
        account.setEmail(responseData.getEmail());
        account.setFirstName(responseData.getFirstName());
        account.setLastName(responseData.getLastName());
        account.setIndustry(responseData.getIndustry());

        accountService.createAccount(account, savedUser.getId());
        savedUser.setAccountId(account.getId());

        return userRepository.save(savedUser);
    }

    public User registerCompany(CompanyRegistrationRequest responseData) {
        User user = new User();
        user.setEmail(responseData.getEmail());
        user.setPassword(passwordEncoder.encode(responseData.getPassword()));
        user.setRole(Role.COMPANY);

        User savedUser = userRepository.save(user); // I need this, because user id is generated after save

        Company company = new Company();
        company.setUserId(savedUser.getId());
        company.setEmail(responseData.getEmail());
        company.setName(responseData.getName());
        company.setWebsite(responseData.getWebsite());
        company.setIndustry(responseData.getIndustry());
        company.setEmployeeCount(responseData.getEmployeeCount());
        company.setCity(responseData.getCity());
        company.setCountry(responseData.getCountry());
        company.setAbout(responseData.getAbout());
        company.setAddress(responseData.getAddress());
        company.setHeadline(responseData.getHeadline());


        companyService.createCompany(company, savedUser.getId());
        savedUser.setCompanyId(company.getId());

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
