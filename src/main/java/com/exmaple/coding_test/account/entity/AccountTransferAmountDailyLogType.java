package com.exmaple.coding_test.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountTransferAmountDailyLogType {
    DEPOSIT(Long.MAX_VALUE),
    WITHDRAW(1_000_000),
    ACCOUNT_TRANSFER(3_000_000),
    ;

    private final long defaultLimitAmount;
}
