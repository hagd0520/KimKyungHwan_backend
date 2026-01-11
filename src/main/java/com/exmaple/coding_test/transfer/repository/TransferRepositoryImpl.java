package com.exmaple.coding_test.transfer.repository;

import com.exmaple.coding_test.account.entity.Account;
import com.exmaple.coding_test.transfer.entity.Transfer;
import com.exmaple.coding_test.transfer.entity.TransferType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

import static com.exmaple.coding_test.transfer.entity.QTransfer.transfer;

@Repository
@RequiredArgsConstructor
public class TransferRepositoryImpl implements TransferRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Transfer> findAll(Account account, TransferType type, LocalDateTime startedAt, LocalDateTime endedAt, Pageable pageable) {
        BooleanBuilder conditions = new BooleanBuilder()
                .and(transfer.subjectAccount.eq(account))
                .and(typeEq(type))
                .and(transfer.createdAt.between(startedAt, endedAt));

        List<Transfer> transfers = queryFactory.selectFrom(transfer)
                .where(conditions)
                .orderBy(transfer.transferId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(Wildcard.count)
                .from(transfer)
                .where(conditions);

        return PageableExecutionUtils.getPage(transfers, pageable, totalCount::fetchOne);
    }

    private BooleanBuilder typeEq(TransferType type) {
        return nullSafeBooleanBuilder(() -> transfer.type.eq(type));
    }

    private BooleanBuilder nullSafeBooleanBuilder(Supplier<BooleanExpression> supplier) {
        try {
            return new BooleanBuilder(supplier.get());
        } catch (IllegalArgumentException | NullPointerException e) {
            return new BooleanBuilder();
        }
    }
}
