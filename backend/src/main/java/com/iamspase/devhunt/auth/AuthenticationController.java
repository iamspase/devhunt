package com.iamspase.devhunt.auth;

import com.iamspase.devhunt.account.AccountService;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserDTO;
import com.iamspase.devhunt.user.UserRepository;
import com.iamspase.devhunt.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationController(
            AuthenticationService authenticationService,
            UserRepository userRepository,
            JwtService jwtService,
            AccountService accountService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRegistrationRequest registerResponseDto) {
        if (authenticationService.isEmailTaken(registerResponseDto.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email already exists");

            log.error("The provided email already exists: {}", registerResponseDto.getEmail());

            return ResponseEntity.badRequest().body(response);
        }

        User user = authenticationService.register(registerResponseDto);
        UserDTO userDTO = userService.toUserDto(user);

        log.info("Successfully registered user: {}", user.getEmail());

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/signup/company")
    public ResponseEntity<?> signUpCompany(@RequestBody CompanyRegistrationRequest companyRegisterRequest) {
        if (authenticationService.isEmailTaken(companyRegisterRequest.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email already exists");

            log.info("The provided email already exists: {}", companyRegisterRequest.getEmail());

            return ResponseEntity.badRequest().body(response);
        }

        User user = authenticationService.registerCompany(companyRegisterRequest);
        UserDTO userDTO = userService.toUserDto(user);
        log.info("Successfully registered company: {}", user.getEmail());

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginResponseDto) {
        User user = authenticationService.authenticate(loginResponseDto);
        String jwtToken = jwtService.generateToken(user);

        AuthResponse loginResponse = new AuthResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());


        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(loginResponse);
    }
}
