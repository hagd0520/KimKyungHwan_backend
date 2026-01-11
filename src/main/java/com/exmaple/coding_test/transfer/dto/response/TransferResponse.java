package com.exmaple.coding_test.transfer.dto.response;

import com.exmaple.coding_test.transfer.entity.Transfer;
import com.exmaple.coding_test.transfer.entity.TransferType;

public record TransferResponse(
        long transferId,
        long subjectAccountId,
        Long receiverAccountId,
        String receiverName,
        Long senderAccountId,
        String senderName,
        TransferType type,
        String message,
        long wholeAmount,
        long transferFee,
        long amount,
        long remainAmount
) {
    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
                transfer.getTransferId(),
                transfer.getSubjectAccount().getAccountId(),
                transfer.getReceiverAccount().getAccountId(),
                transfer.getReceiverName(),
                transfer.getSenderAccount() != null ? transfer.getSenderAccount().getAccountId() : 0,
                transfer.getSenderName(),
                transfer.getType(),
                transfer.getMessage(),
                transfer.getWholeAmount(),
                transfer.getTransferFee(),
                transfer.getAmount(),
                transfer.getRemainAmount()
        );
    }
}
