package com.exmaple.coding_test.transfer.service;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLog;
import com.exmaple.coding_test.account.entity.AccountTransferAmountDailyLogType;
import com.exmaple.coding_test.account.service.AccountService;
import com.exmaple.coding_test.account.service.AccountTransferAmountDailyLogService;
import com.exmaple.coding_test.account.service.AccountTransferDailyLimitService;
import com.exmaple.coding_test.transfer.dto.request.TransferAccountTransferRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferDepositRequest;
import com.exmaple.coding_test.transfer.dto.request.TransferWithdrawRequest;
import com.exmaple.coding_test.transfer.dto.response.TransferResponse;
import com.exmaple.coding_test.transfer.entity.Transfer;
import com.exmaple.coding_test.transfer.entity.TransferFeeOptionType;
import com.exmaple.coding_test.transfer.entity.TransferType;
import com.exmaple.coding_test.transfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountService accountService;
    private final TransferRepository transferRepository;
    private final TransferFeeOptionService transferFeeOptionService;
    private final AccountTransferDailyLimitService accountTransferDailyLimitService;
    private final AccountTransferAmountDailyLogService accountTransferAmountDailyLogService;

    @Transactional
    public TransferResponse deposit(TransferDepositRequest request) {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Account receiverAccount = accountService.findByAccountNumber(request.receiverAccountNumber());
        double feeRate = transferFeeOptionService.getFeeRate(TransferFeeOptionType.DEPOSIT);
        Transfer latestTransfer = findLatestTransferByAccount(receiverAccount);

        Transfer newTransfer = Transfer.of(
                TransferType.DEPOSIT,
                receiverAccount,
                receiverAccount,
                receiverAccount.getUsername(),
                null,
                request.senderName(),
                request.message(),
                request.amount(),
                feeRate,
                latestTransfer.getRemainAmount()
        );
        latestTransfer.updateNextTransfer(newTransfer);
        validateDailyLimit(receiverAccount, newTransfer, AccountTransferAmountDailyLogType.DEPOSIT, date);

        newTransfer = transferRepository.save(newTransfer);
        return TransferResponse.from(newTransfer);
    }

    public TransferResponse withdraw(TransferWithdrawRequest request) {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Account senderAccount = accountService.findByAccountNumberAndPassword(request.senderAccountNumber(), request.password());
        double feeRate = transferFeeOptionService.getFeeRate(TransferFeeOptionType.WITHDRAW);
        Transfer latestTransfer = findLatestTransferByAccount(senderAccount);

        Transfer newTransfer = Transfer.of(
                TransferType.WITHDRAW,
                senderAccount,
                null,
                request.receiverName(),
                senderAccount,
                senderAccount.getUsername(),
                request.message(),
                -request.amount(),
                feeRate,
                latestTransfer.getRemainAmount()
        );
        latestTransfer.updateNextTransfer(newTransfer);
        validateDailyLimit(senderAccount, newTransfer, AccountTransferAmountDailyLogType.WITHDRAW, date);

        newTransfer = transferRepository.save(newTransfer);
        return TransferResponse.from(newTransfer);
    }

    public TransferResponse transfer(TransferAccountTransferRequest request) {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Account receiverAccount = accountService.findByAccountNumber(request.receiverAccountNumber());
        double receiverFeeRate = transferFeeOptionService.getFeeRate(TransferFeeOptionType.DEPOSIT);
        Account senderAccount = accountService.findByAccountNumberAndPassword(request.senderAccountNumber(), request.password());
        double senderFeeRate = transferFeeOptionService.getFeeRate(TransferFeeOptionType.ACCOUNT_TRANSFER);
        Transfer latestReceiverTransfer = findLatestTransferByAccount(receiverAccount);
        Transfer latestSenderTransfer = findLatestTransferByAccount(senderAccount);

        Transfer newReceiverTransfer = Transfer.of(
                TransferType.DEPOSIT,
                receiverAccount,
                receiverAccount,
                receiverAccount.getUsername(),
                senderAccount,
                senderAccount.getUsername(),
                request.message(),
                request.amount(),
                receiverFeeRate,
                latestReceiverTransfer.getRemainAmount()
        );
        latestReceiverTransfer.updateNextTransfer(newReceiverTransfer);
        validateDailyLimit(receiverAccount, newReceiverTransfer, AccountTransferAmountDailyLogType.DEPOSIT, date);

        transferRepository.save(newReceiverTransfer);

        Transfer newSenderTransfer = Transfer.of(
                TransferType.WITHDRAW,
                senderAccount,
                receiverAccount,
                receiverAccount.getUsername(),
                senderAccount,
                senderAccount.getUsername(),
                request.message(),
                -request.amount(),
                senderFeeRate,
                latestSenderTransfer.getRemainAmount()
        );
        latestSenderTransfer.updateNextTransfer(newSenderTransfer);
        validateDailyLimit(receiverAccount, newSenderTransfer, AccountTransferAmountDailyLogType.ACCOUNT_TRANSFER, date);

        newSenderTransfer = transferRepository.save(newSenderTransfer);

        return TransferResponse.from(newSenderTransfer);
    }

    private void validateDailyLimit(Account account, Transfer newTransfer, AccountTransferAmountDailyLogType type, LocalDate date) {
        AccountTransferAmountDailyLog accountTransferAmountDailyLog = accountTransferAmountDailyLogService.find(account, type, date);
        long limitAmount = accountTransferDailyLimitService.getLimitAmount(account.getAccountId(), type);
        accountTransferAmountDailyLog.increaseTotalAmount(newTransfer.getAmount());
        if (accountTransferAmountDailyLog.getTotalAmount() > limitAmount) {
            throw new RuntimeException("Daily %s limit exceeded".formatted(type.name()));
        }
    }

    private Transfer findLatestTransferByAccount(Account account) {
        Transfer transfer = transferRepository.findFirstBySubjectAccountOrderByTransferIdDesc(account)
                .orElseThrow(() -> new RuntimeException("Latest transfer not found"));
        while (transfer.isNotLatest()) {
            transfer = findLatestTransferByAccount(account);
        }
        return transfer;
    }

    public List<TransferResponse> findAll(String accountNumber, TransferType transferType, LocalDateTime startedAt, LocalDateTime endedAt, Pageable pageable) {
        return null;
    }
}
