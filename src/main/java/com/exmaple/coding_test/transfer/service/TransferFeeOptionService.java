package com.exmaple.coding_test.transfer.service;

import com.exmaple.coding_test.transfer.dto.request.TransferFeeOptionUpdateRequest;
import com.exmaple.coding_test.transfer.entity.TransferFeeOption;
import com.exmaple.coding_test.transfer.entity.TransferFeeOptionType;
import com.exmaple.coding_test.transfer.repository.TransferFeeOptionCacheRepository;
import com.exmaple.coding_test.transfer.repository.TransferFeeOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TransferFeeOptionService {
    private final TransferFeeOptionRepository transferFeeOptionRepository;
    private final TransferFeeOptionCacheRepository transferFeeOptionCacheRepository;

    public static final Duration FEE_RATE_CACHE_DURATION = Duration.ofMinutes(30);

    @Transactional
    public void update(TransferFeeOptionUpdateRequest request) {
        TransferFeeOption option = findOrInit(request.type());
        option.updateFeeRate(request.feeRate());
        transferFeeOptionRepository.save(option);
        transferFeeOptionCacheRepository.set(option.getType(), option.getFeeRate(), FEE_RATE_CACHE_DURATION);
    }

    public double getFeeRate(TransferFeeOptionType type) {
        Double feeRate = transferFeeOptionCacheRepository.get(type);
        if (feeRate == null) {
            TransferFeeOption option = findOrInit(type);
            transferFeeOptionCacheRepository.set(type, option.getFeeRate(), FEE_RATE_CACHE_DURATION);
            return option.getFeeRate();
        } else {
            return feeRate;
        }
    }

    private TransferFeeOption findOrInit(TransferFeeOptionType type) {
        return transferFeeOptionRepository.findByType(type).orElseGet(() -> init(type));
    }

    private TransferFeeOption init(TransferFeeOptionType type) {
        return transferFeeOptionRepository.save(TransferFeeOption.init(type));
    }
}
