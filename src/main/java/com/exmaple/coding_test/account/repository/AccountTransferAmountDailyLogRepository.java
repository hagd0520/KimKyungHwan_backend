package com.exmaple.coding_test.account.repository;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLog;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AccountTransferAmountDailyLogRepository extends JpaRepository<AccountTransferAmountDailyLog, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountTransferAmountDailyLog> findByAccountAndTypeAndDate(Account account, AccountTransferAmountDailyLogType type, LocalDate date);
}
