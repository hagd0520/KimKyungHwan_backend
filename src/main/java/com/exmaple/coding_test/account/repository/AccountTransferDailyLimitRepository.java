package com.exmaple.coding_test.account.repository;

import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import com.exmaple.coding_test.account.entity.AccountTransferDailyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTransferDailyLimitRepository extends JpaRepository<AccountTransferDailyLimit, Long> {
    Optional<AccountTransferDailyLimit> findByAccountAccountIdAndType(Long accountAccountId, AccountTransferAmountDailyLogType type);
}
