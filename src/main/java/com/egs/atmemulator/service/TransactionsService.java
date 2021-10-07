package com.egs.atmemulator.service;

import com.egs.atmemulator.dto.TransactionDTO;
import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.model.Transactions;

import java.util.Date;
import java.util.List;

public interface TransactionsService {

    Transactions add(Transactions transactions);
    List<Transactions> search(Transactions transactions);
    List<Transactions> search(TransactionDTO transactionDTO);
    List<Transactions> findByUserId(Long userId);
    List<Transactions> findByCardNumber(Long cardNumber);
    List<Transactions> findByType(TransactionType transactionType);
    List<Transactions> findBySourceCardNumberAndDateAndTransactionStatus(Long sourceCard, Date date, TransactionStatus status);

}
