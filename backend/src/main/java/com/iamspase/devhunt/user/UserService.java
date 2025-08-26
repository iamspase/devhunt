package com.iamspase.devhunt.user;

import com.iamspase.devhunt.account.Account;
import com.iamspase.devhunt.account.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    /* The methods for creating a user are in the AuthService.java, as there's the handling for auth */

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // TODO: add update user method

    // Deletes the user by their ID, and the account corresponding to that user.
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            Account account = user.get().getAccount();
            if(account != null) {
                accountRepository.delete(account);
            }
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
