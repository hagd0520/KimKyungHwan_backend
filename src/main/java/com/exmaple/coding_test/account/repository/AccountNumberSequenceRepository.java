package com.exmaple.coding_test.account.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountNumberSequenceRepository {
    private final static String ACCOUNT_NUMBER_SEQUENCE_KEY = "account_number_sequence";

    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public Long getNextSequence() {
        return redisTemplate.opsForValue().increment(ACCOUNT_NUMBER_SEQUENCE_KEY);;
    }
}
