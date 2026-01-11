package com.exmaple.coding_test.transfer.dto.request;

import jakarta.validation.constraints.Min;

public record TransferAccountTransferRequest(
        String receiverAccountNumber,
        String senderAccountNumber,
        String password,
        @Min(0) long amount,
        String message
) {
}
