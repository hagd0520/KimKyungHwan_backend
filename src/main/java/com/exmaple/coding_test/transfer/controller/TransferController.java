package com.exmaple.coding_test.transfer.controller;

import com.exmaple.coding_test.transfer.dto.request.TransferAccountTransferRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferDepositRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferWithdrawRequest;
import com.exmaple.coding_test.transfer.dto.response.TransferResponse;
import com.exmaple.coding_test.transfer.entity.TransferType;
import com.exmaple.coding_test.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/api/v1/transfers/deposit")
    public TransferResponse deposit(@Validated @RequestBody TransferDepositRequest request) {
        return transferService.deposit(request);
    }

    @PostMapping("/api/v1/transfers/withdraw")
    public TransferResponse withdraw(@Validated @RequestBody TransferWithdrawRequest request) {
        return transferService.withdraw(request);
    }

    @PostMapping("/api/v1/transfers/account-transfer")
    public TransferResponse accountTransfer(@Validated @RequestBody TransferAccountTransferRequest request) {
        return transferService.transfer(request);
    }

    @GetMapping("/api/v1/transfers")
    public Page<TransferResponse> findAll(
            @RequestParam(required = true) String accountNumber,
            @RequestParam(required = false) TransferType type,
            @RequestParam(required = true) LocalDateTime startedAt,
            @RequestParam(required = true) LocalDateTime endedAt,
            Pageable pageable
    ) {
        return transferService.findAll(
                accountNumber,
                type,
                startedAt,
                endedAt,
                pageable
        );
    }
}
