package com.iamspase.devhunt.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long userId) {
        Account account = accountService.getAccount(userId);
        return ResponseEntity.ok(account);
    }

    @PostMapping("{userId}/profile-picture")
    @PreAuthorize("authentication.principal.id == #userId")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        accountService.uploadProfilePicture(userId, file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).build();
    }


    @GetMapping("{userId}/profile-picture")
    public ResponseEntity<String> getProfilePicture(@PathVariable Long userId) {
        String url = accountService.getProfilePictureUrl(userId);

        return ResponseEntity.ok(url);
    }
}
