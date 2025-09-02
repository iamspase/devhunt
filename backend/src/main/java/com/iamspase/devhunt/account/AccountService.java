package com.iamspase.devhunt.account;

import com.iamspase.devhunt.aws.S3Service;
import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final S3Service s3Service;

    public AccountService(AccountRepository accountRepository, UserService userService, S3Service s3Service) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.s3Service = s3Service;
    }

    public void createAccount(Account account, Long userId) {
        User user = userService.getUserById(userId);

        if(user == null){
            throw new EntityNotFoundException("User not found");
        }

        account.setUserId(user.getId());
        accountRepository.save(account);
    }

    @Transactional
    public Account getAccount(Long userId) {
        User user = userService.getUserById(userId);

        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }

        Account account = accountRepository.findAccountByUserId(userId);

        if(account == null) {
            throw new EntityNotFoundException("Account not found");
        }

        return account;
    }

    // Returns a presigned url for the profile picture
    public String getProfilePictureUrl(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        Account account = accountRepository.findAccountByUserId(userId);

        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }

        // Generate a presigned url for the profile picture every time
        if (account.getPhotoUrl() != null) {
            log.info("Generating presigned url for profile picture");
            return s3Service.generatePresignedUrl("devhuntbucket", account.getPhotoUrl());
        }


        return null;
    }

    @Transactional
    public void uploadProfilePicture(Long userId, MultipartFile profilePicture) {
        log.info("Uploading profile picture");

        User user = userService.getUserById(userId);

        log.info(profilePicture.getOriginalFilename());
        Account account = accountRepository.findFirstById((user.getAccountId()));

        String key = "profile-pictures/" + account.getUserId() + "-" + profilePicture.getOriginalFilename();

        try {
            s3Service.uploadFile("devhuntbucket", key, profilePicture);

            account.setPhotoUrl(key);
            accountRepository.save(account);
        }
        catch (IOException exception) {
            log.error("Failed to upload file to S3: {}", exception.getMessage());
        }
    }
}
