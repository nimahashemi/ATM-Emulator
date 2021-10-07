package com.egs.atmemulator.dto;

import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private Long userId;
    private TransactionType type;
    private Date from;
    private Date to;
    private Long sourceCardNumber;
    private Long destinationCardNumber;
    private Long amount;
    private TransactionStatus transactionStatus;
}
