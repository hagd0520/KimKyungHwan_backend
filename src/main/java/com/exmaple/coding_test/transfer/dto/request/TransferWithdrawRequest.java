package com.exmaple.coding_test.transfer.dto.request;

import jakarta.validation.constraints.Min;

public record TransferWithdrawRequest(
        String senderAccountNumber,
        String receiverName,
        String password,
        @Min(0) long amount,
        String message
) {
}
