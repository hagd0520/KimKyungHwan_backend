package com.exmaple.coding_test.account.controller;

import com.exmaple.coding_test.account.dto.request.AccountTransferDailyLimitUpdateRequest;
import com.exmaple.coding_test.account.service.AccountTransferDailyLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountTransferDailyLimitController {
    private final AccountTransferDailyLimitService accountTransferDailyLimitService;

    @PutMapping("/api/v1/accounts/{accountId}/transfer-daily-limit")
    public void update(
            @PathVariable long accountId,
            @RequestBody AccountTransferDailyLimitUpdateRequest request
    ) {
        accountTransferDailyLimitService.update(accountId, request);
    }
}
