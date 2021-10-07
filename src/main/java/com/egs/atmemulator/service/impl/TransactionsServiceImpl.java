package com.egs.atmemulator.service.impl;

import com.egs.atmemulator.dto.TransactionDTO;
import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.model.Transactions;
import com.egs.atmemulator.repository.TransactionsRepository;
import com.egs.atmemulator.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private static final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    private TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }


    @Override
    public Transactions add(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    @Override
    public List<Transactions> search(Transactions transactions) {
        return transactionsRepository.search(transactions.getUserId(),
                transactions.getType(),
                transactions.getDate(),
                transactions.getDate(),
                transactions.getSourceCardNumber(),
                transactions.getDestinationCardNumber(),
                transactions.getAmount(),
                transactions.getTransactionStatus());
    }
    public List<Transactions> search(TransactionDTO transactionDTO) {
        return transactionsRepository.search(transactionDTO.getUserId(),
                transactionDTO.getType(),
                transactionDTO.getFrom(),
                transactionDTO.getTo(),
                transactionDTO.getSourceCardNumber(),
                transactionDTO.getDestinationCardNumber(),
                transactionDTO.getAmount(),
                transactionDTO.getTransactionStatus());
    }

    @Override
    public List<Transactions> findByUserId(Long userId) {
        return transactionsRepository.findByUserId(userId);
    }

    @Override
    public List<Transactions> findByCardNumber(Long cardNumber) {
        return transactionsRepository.findBySourceCardNumberAndDestinationCardNumber(cardNumber, cardNumber);
    }

    @Override
    public List<Transactions> findByType(TransactionType transactionType) {
        return transactionsRepository.findByType(transactionType);
    }

    @Override
    public List<Transactions> findBySourceCardNumberAndDateAndTransactionStatus(Long sourceCard, Date date, TransactionStatus status) {
        return transactionsRepository.findBySourceCardNumberAndDateAndTransactionStatus(sourceCard, date, status);
    }
}
