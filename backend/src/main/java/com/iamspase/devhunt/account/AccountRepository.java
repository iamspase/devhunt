package com.iamspase.devhunt.account;

import com.iamspase.devhunt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findAccountByUserId(Long userId);

    Account findFirstById(Long id);
}
