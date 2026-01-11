package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.entity.AccountTransferDailyLimit;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import com.exmaple.coding_test.account.repository.AccountTransferDailyLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTransferDailyLimitInitializer {
    private final AccountTransferDailyLimitRepository accountTransferDailyLimitRepository;

    @Transactional
    public void init(Account account) {
        List<AccountTransferDailyLimit> initialDailyLimit = Arrays.stream(AccountTransferAmountDailyLogType.values())
                .map(type -> AccountTransferDailyLimit.of(account, type, type.getDefaultLimitAmount()))
                .toList();

        accountTransferDailyLimitRepository.saveAll(initialDailyLimit);
    }
}
