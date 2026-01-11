package com.exmaple.coding_test.transfer.entity;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Table(
        indexes = {
                @Index(name = "idx_subject_account_id_asc_created_at_asc", columnList = "subjectAccountId, createdAt")
        }
)
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Transfer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long transferId;

    @OneToOne(fetch = LAZY, optional = true)
    @JoinColumn(name = "next_transfer_id")
    private Transfer nextTransfer;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "subject_account_id")
    private Account subjectAccount;

    @ManyToOne(fetch = LAZY, optional = true)
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;
    @Column(nullable = false)
    private String receiverName;

    @ManyToOne(fetch = LAZY, optional = true)
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;
    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private TransferType type;
    @Column(nullable = true)
    private String message;

    @Column(nullable = false)
    private long wholeAmount;
    @Column(nullable = false)
    private long transferFee;
    @Column(nullable = false)
    private long amount;
    private long remainAmount;

    public static Transfer of(
            TransferType type,
            Account subjectAccount,
            Account receiverAccount,
            String receiverName,
            Account senderAccount,
            String senderName,
            String message,
            long amount,
            double feeRate,
            long latestRemainAmount
    ) {
        Transfer transfer = new Transfer();
        long transferFee = (long) (amount * (feeRate / 100));
        long wholeAmount = amount + transferFee;
        long remainAmount = latestRemainAmount + wholeAmount;

        if (remainAmount < 0) {
            throw new IllegalArgumentException("Insufficient funds in the subject account.");
        }

        transfer.subjectAccount = subjectAccount;
        transfer.receiverAccount = receiverAccount;
        transfer.receiverName = receiverName;
        transfer.senderAccount = senderAccount;
        transfer.senderName = senderName;
        transfer.type = type;
        transfer.message = message;
        transfer.wholeAmount = wholeAmount;
        transfer.transferFee = transferFee;
        transfer.amount = amount;
        transfer.remainAmount = remainAmount;
        return transfer;
    }

    public void updateNextTransfer(Transfer nextTransfer) {
        this.nextTransfer = nextTransfer;
    }

    public boolean isNotLatest() {
        return this.nextTransfer != null;
    }
}
