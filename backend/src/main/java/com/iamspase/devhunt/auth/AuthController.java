package com.iamspase.devhunt.auth;

import com.iamspase.devhunt.services.AuthenticationService;
import com.iamspase.devhunt.services.JwtService;
import com.iamspase.devhunt.user.LoginResponseDto;
import com.iamspase.devhunt.user.RegisterResponseDto;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private JwtService jwtService;

    public AuthController(
            AuthenticationService authenticationService,
            UserRepository userRepository,
            JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody RegisterResponseDto registerResponseDto) {
        User user = authenticationService.register(registerResponseDto);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginResponseDto loginResponseDto) {
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
