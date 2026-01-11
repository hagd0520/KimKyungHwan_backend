package com.exmaple.coding_test.transfer.controller;

import com.exmaple.coding_test.transfer.dto.request.TransferFeeOptionUpdateRequest;
import com.exmaple.coding_test.transfer.service.TransferFeeOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferFeeOptionController {
    private final TransferFeeOptionService transferFeeOptionService;

    @PutMapping("/api/v1/transfers/fee-options")
    public void update(@RequestBody TransferFeeOptionUpdateRequest request) {
        transferFeeOptionService.update(request);
    }
}
