package com.iamspase.devhunt.account;

import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public void createAccount(Account account, Long userId) {
        User user = userService.getUserById(userId);

        if(user == null){
            throw new EntityNotFoundException("User not found");
        }

        account.setUser(user);
        accountRepository.save(account);
    }

    public Account getAccount(Long userId) {
        User user = userService.getUserById(userId);

        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }

        return accountRepository.findAccountByUser(user);
    }
}
