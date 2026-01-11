package com.exmaple.coding_test.transfer.dto.request;

import com.exmaple.coding_test.transfer.entity.TransferFeeOptionType;

public record TransferFeeOptionUpdateRequest(
        TransferFeeOptionType type,
        double feeRate
) {
}
