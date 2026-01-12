package com.exmaple.coding_test.transfer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferFeeOption {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long transferFeeOptionId;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TransferFeeOptionType type;
    @Column(nullable = false)
    private double feeRate;

    public static TransferFeeOption init(TransferFeeOptionType type) {
        return of(type, type.getDefaultFeeRate());
    }

    private static TransferFeeOption of(TransferFeeOptionType type, double feeRate) {
        TransferFeeOption option = new TransferFeeOption();
        option.type = type;
        option.feeRate = feeRate;
        return option;
    }

    public void updateFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }
}
