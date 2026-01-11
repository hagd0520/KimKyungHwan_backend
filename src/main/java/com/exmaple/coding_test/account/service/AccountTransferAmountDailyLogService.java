package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLog;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import com.exmaple.coding_test.account.repository.AccountTransferAmountDailyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountTransferAmountDailyLogService {
    private final AccountTransferAmountDailyLogRepository accountTransferAmountDailyLogRepository;

    public AccountTransferAmountDailyLog find(Account account, AccountTransferAmountDailyLogType type, LocalDate date) {
        return accountTransferAmountDailyLogRepository.findByAccountAndTypeAndDate(account, type, date)
                .orElseGet(() -> init(account, type, date));
    }

    private AccountTransferAmountDailyLog init(Account account, AccountTransferAmountDailyLogType type, LocalDate date) {
        AccountTransferAmountDailyLog initialDailyLog = AccountTransferAmountDailyLog.init(account, type, date);
        return accountTransferAmountDailyLogRepository.save(initialDailyLog);
    }
}
