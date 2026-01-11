package com.exmaple.coding_test.transfer.dto.request;

import jakarta.validation.constraints.Min;

public record TransferDepositRequest(
        String receiverAccountNumber,
        String senderName,
        @Min(0) long amount,
        String message
) {
}
