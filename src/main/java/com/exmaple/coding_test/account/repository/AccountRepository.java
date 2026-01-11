package com.exmaple.coding_test.account.repository;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.support.entity.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumberAndStatus(String accountNumber, EntityStatus active);
}
