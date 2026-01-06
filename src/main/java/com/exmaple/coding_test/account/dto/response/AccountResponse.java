package com.exmaple.coding_test.account.dto.response;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.support.entity.EntityStatus;

import java.time.LocalDateTime;

public record AccountResponse(
        String username,
        String accountNumber,
        EntityStatus status,
        LocalDateTime createdAt
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getUsername(),
                account.getAccountNumber(),
                account.getStatus(),
                account.getCreatedAt()
        );
    }
}
