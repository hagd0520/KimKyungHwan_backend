package com.exmaple.coding_test.transfer.repository;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.transfer.entity.Transfer;
import com.exmaple.coding_test.transfer.entity.TransferType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TransferRepositoryCustom {

    Page<Transfer> findAll(Account account, TransferType transferType, LocalDateTime startedAt, LocalDateTime endedAt, Pageable pageable);
}
