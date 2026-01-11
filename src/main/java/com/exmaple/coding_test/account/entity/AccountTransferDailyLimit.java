package com.exmaple.coding_test.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(
        indexes = {
                @Index(name = "idx_account_id_asc_type_asc", columnList = "accountId, type", unique = true)
        }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTransferDailyLimit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long accountTransferDailyLimitId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(nullable = false)
    private AccountTransferAmountDailyLogType type;
    @Column(nullable = false)
    private long limitAmount;

    public static AccountTransferDailyLimit of(Account account, AccountTransferAmountDailyLogType type, long limitAmount) {
        AccountTransferDailyLimit dailyLimit = new AccountTransferDailyLimit();
        dailyLimit.account = account;
        dailyLimit.type = type;
        dailyLimit.limitAmount = limitAmount;
        return dailyLimit;
    }

    public void updateLimitAmount(long limitAmount) {
        this.limitAmount = limitAmount;
    }
}
