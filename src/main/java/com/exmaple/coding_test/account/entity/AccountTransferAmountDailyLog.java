package com.exmaple.coding_test.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Table(
        indexes = {
                @Index(name = "idx_account_id_asc_type_asc_date_asc", columnList = "accountId, type, date", unique = true)
        }
)
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AccountTransferAmountDailyLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long accountTransferAmountDailyLogId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(nullable = false)
    private AccountTransferAmountDailyLogType type;
    @Column(nullable = false)
    private LocalDate date;
    private long totalAmount;

    public static AccountTransferAmountDailyLog init(Account account, AccountTransferAmountDailyLogType type, LocalDate date) {
        AccountTransferAmountDailyLog dailyLog = new AccountTransferAmountDailyLog();
        dailyLog.account = account;
        dailyLog.type = type;
        dailyLog.date = date;
        dailyLog.totalAmount = 0L;
        return dailyLog;
    }

    public void increaseTotalAmount(long amount) {
        this.totalAmount += amount;
    }
}
