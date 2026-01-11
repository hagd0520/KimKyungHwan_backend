package com.exmaple.coding_test.transfer.repository;

import com.exmaple.coding_test.transfer.entity.TransferFeeOption;
import com.exmaple.coding_test.transfer.entity.TransferFeeOptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferFeeOptionRepository extends JpaRepository<TransferFeeOption, Long> {
    Optional<TransferFeeOption> findByType(TransferFeeOptionType type);
}
