package com.exmaple.coding_test.account.dto.request;

import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;

public record AccountTransferDailyLimitUpdateRequest(
        AccountTransferAmountDailyLogType type,
        long amount
) {
}
