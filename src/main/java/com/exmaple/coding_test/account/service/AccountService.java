package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.dto.request.AccountCreateRequest;
import com.exmaple.coding_test.account.dto.response.AccountResponse;
import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Transactional
    public AccountResponse create(AccountCreateRequest request) {
        Account account = Account.of(accountNumberGenerator.generate(), request.username());
        Account savedAccount = accountRepository.save(account);
        return AccountResponse.from(savedAccount);
    }

    @Transactional
    public void softDelete(String accountNumber) {
        Account account = findByAccountNumber(accountNumber);
        account.delete();
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}
