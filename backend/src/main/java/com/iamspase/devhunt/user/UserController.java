package com.iamspase.devhunt.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        UserDTO userDTO = userService.toUserDto(user);

        user.setRole(user.getRole());

        return ResponseEntity.ok().body(userDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable  Long id) {
        User user = userService.getUserById(id);

        UserDTO userDTO = userService.toUserDto(user);

        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!user.getId().equals(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "You can only delete your own account");
            return ResponseEntity.status(403).body(response);
        }

        userService.deleteUser(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User successfully deleted");

        return ResponseEntity.ok().body(response);
    }
}
