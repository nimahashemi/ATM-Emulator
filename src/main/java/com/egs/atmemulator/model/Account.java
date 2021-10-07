package com.egs.atmemulator.model;


import com.egs.atmemulator.enums.AccountStatus;
import com.egs.atmemulator.enums.AccountType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    @Column(name = "account_number", unique = true)
    @Size(min = 6, max = 16, message = "Account number must be between 6 & 16")
    private Long accountNumber;

    @NotNull
    @Column(name = "balance")
    @Builder.Default
    @Size(min = 4)
    private Long balance;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    // Set default value for account balance if it is null
    public Account() {
        balance = Long.valueOf(1000);
    }
}
