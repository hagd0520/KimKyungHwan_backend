package com.exmaple.coding_test.account.controller;

import com.exmaple.coding_test.account.dto.request.AccountCreateRequest;
import com.exmaple.coding_test.account.dto.response.AccountResponse;
import com.exmaple.coding_test.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public AccountResponse create(
            @RequestBody AccountCreateRequest request
    ) {
        return accountService.create(request);
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public void delete(
            @PathVariable String accountNumber
    ) {
        accountService.softDelete(accountNumber);
    }
}
