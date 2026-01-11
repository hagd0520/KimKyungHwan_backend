package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.dto.request.AccountTransferDailyLimitUpdateRequest;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import com.exmaple.coding_test.account.entity.AccountTransferDailyLimit;
import com.exmaple.coding_test.account.repository.AccountTransferDailyLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountTransferDailyLimitService {
    private final AccountTransferDailyLimitRepository accountTransferDailyLimitRepository;

    @Transactional
    public void update(long accountId, AccountTransferDailyLimitUpdateRequest request) {
        AccountTransferDailyLimit dailyLimit = findByAccountIdAndType(accountId, request.type());
        dailyLimit.updateLimitAmount(request.amount());
    }

    public long getLimitAmount(long accountId, AccountTransferAmountDailyLogType type) {
        AccountTransferDailyLimit dailyLimit = findByAccountIdAndType(accountId, type);
        return dailyLimit.getLimitAmount();
    }

    private AccountTransferDailyLimit findByAccountIdAndType(long accountId, AccountTransferAmountDailyLogType type) {
        return accountTransferDailyLimitRepository.findByAccountAccountIdAndType(accountId, type)
                .orElseThrow(() -> new RuntimeException("Limit initialization error"));
    }
}
