package com.exmaple.coding_test.account.entity;

import com.exmaple.coding_test.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long accountId;

    @Column(unique = true)
    private String accountNumber;
    private String username;
    private String password;

    public static Account of(String accountNumber, String username, String password) {
        Account account = new Account();
        account.accountNumber = String.format(accountNumber);
        account.username = username;
        account.password = password;
        return account;
    }
}
