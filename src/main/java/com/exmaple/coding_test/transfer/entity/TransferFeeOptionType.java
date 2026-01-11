package com.exmaple.coding_test.transfer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferFeeOptionType {
    DEPOSIT(0),
    WITHDRAW(0),
    ACCOUNT_TRANSFER(1.0),
    ;

    private final double defaultFeeRate;
}
