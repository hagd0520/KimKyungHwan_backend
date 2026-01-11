package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.dto.request.AccountCreateRequest;
import com.exmaple.coding_test.account.dto.response.AccountResponse;
import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.repository.AccountRepository;
import com.exmaple.coding_test.transfer.service.FirstEmptyTransferInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final FirstEmptyTransferInitializer firstEmptyTransferInitializer;
    private final AccountTransferDailyLimitInitializer accountTransferDailyLimitInitializer;

    @Transactional
    public AccountResponse create(AccountCreateRequest request) {
        Account account = Account.of(
                accountNumberGenerator.generate(),
                request.username(),
                passwordEncoder.encode(request.password())
        );
        Account savedAccount = accountRepository.save(account);
        firstEmptyTransferInitializer.init(savedAccount);
        accountTransferDailyLimitInitializer.init(account);
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

    public Account findByAccountNumberAndPassword(String accountNumber, String password) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return account;
    }
}
