package com.exmaple.coding_test.account.service;

import com.exmaple.coding_test.account.repository.AccountNumberSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {
    private final AccountNumberSequenceRepository accountNumberSequenceRepository;

    public String generate() {
        return String.format("%010d", accountNumberSequenceRepository.getNextSequence());
    }
}
