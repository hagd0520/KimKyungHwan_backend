package com.exmaple.coding_test.transfer.controller;

import com.exmaple.coding_test.transfer.dto.request.TransferDepositRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferAccountTransferRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferWithdrawRequest;
import com.exmaple.coding_test.transfer.dto.response.TransferResponse;
import com.exmaple.coding_test.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
