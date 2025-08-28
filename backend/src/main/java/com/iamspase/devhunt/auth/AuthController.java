package com.iamspase.devhunt.auth;

import com.iamspase.devhunt.account.AccountService;
import com.iamspase.devhunt.services.JwtService;
import com.iamspase.devhunt.user.LoginRequest;
import com.iamspase.devhunt.user.RegisterRequest;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private AccountService accountService;
    private JwtService jwtService;

    public AuthController(
            AuthenticationService authenticationService,
            UserRepository userRepository,
            JwtService jwtService,
            AccountService accountService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest registerResponseDto) {
        if (authenticationService.isEmailTaken(registerResponseDto.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email already exists");

            return ResponseEntity.badRequest().body(response);
        }
        User user = authenticationService.register(registerResponseDto);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginResponseDto) {
        User user = authenticationService.authenticate(loginResponseDto);
        String jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(loginResponse);
    }
}
