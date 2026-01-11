package com.exmaple.coding_test.transfer.repository;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.transfer.entity.Transfer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Transfer> findFirstBySubjectAccountOrderByTransferIdDesc(Account subjectAccount);
}
