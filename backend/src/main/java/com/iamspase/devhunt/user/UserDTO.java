package com.iamspase.devhunt.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String email;
    private Role role;
    private Long accountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
