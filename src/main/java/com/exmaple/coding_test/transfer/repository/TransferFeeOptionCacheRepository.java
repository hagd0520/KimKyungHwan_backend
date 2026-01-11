package com.exmaple.coding_test.transfer.repository;

import com.exmaple.coding_test.transfer.entity.TransferFeeOptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class TransferFeeOptionCacheRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "transfer_fee_option:%s";

    public void set(TransferFeeOptionType transferType, double feeRate, Duration duration) {
        redisTemplate.opsForValue().set(
                generateKey(transferType),
                String.valueOf(feeRate),
                duration
        );
    }

    public Double get(TransferFeeOptionType transferType) {
        String value = redisTemplate.opsForValue().get(generateKey(transferType));
        if (value != null) {
            return Double.parseDouble(value);
        } else {
            return null;
        }
    }

    private String generateKey(TransferFeeOptionType transferType) {
        return String.format(KEY_FORMAT, transferType);
    }
}
