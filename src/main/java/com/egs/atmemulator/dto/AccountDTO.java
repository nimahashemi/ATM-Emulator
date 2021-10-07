package com.egs.atmemulator.dto;

import com.egs.atmemulator.enums.AccountStatus;
import com.egs.atmemulator.enums.AccountType;
import lombok.Data;

@Data
public class AccountDTO {

    private Long userId;
    private AccountType accountType;
    private Long accountNumber;
    private Long balance;
    private AccountStatus status;
}
