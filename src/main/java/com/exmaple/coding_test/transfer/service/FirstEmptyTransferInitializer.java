package com.exmaple.coding_test.transfer.service;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.transfer.entity.Transfer;
import com.exmaple.coding_test.transfer.entity.TransferType;
import com.exmaple.coding_test.transfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FirstEmptyTransferInitializer {
    private final TransferRepository transferRepository;

    @Transactional
    public void init(Account account) {
        Transfer initTransfer = Transfer.of(
                TransferType.DEPOSIT,
                account,
                account,
                account.getUsername(),
                null,
                "Initial Deposit",
                null,
                0L,
                0.0,
                0L
        );
        transferRepository.save(initTransfer);
    }
}
