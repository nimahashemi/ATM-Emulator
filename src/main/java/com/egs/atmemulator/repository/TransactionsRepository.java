package com.egs.atmemulator.repository;

import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query("SELECT t FROM Transactions t WHERE (?1 is null or t.userId = ?1) " +
            "and (?2 is null or t.type = ?2) " +
            "and (?3 is null or t.date >= ?3) " +
            "and (?4 is null or t.date <= ?4) " +
            "and (?5 is null or t.sourceCardNumber = ?5) " +
            "and (?6 is null or t.destinationCardNumber = ?6) " +
            "and (?7 is null or t.amount = ?7) " +
            "and (?8 is null or t.transactionStatus = ?8) ")
    List<Transactions> search(Long userId,
                              TransactionType type,
                              Date from,
                              Date to,
                              Long sourceCardNumber,
                              Long destinationCardNumber,
                              Long amount,
                              TransactionStatus transactionStatus);
    List<Transactions> findByUserId(Long userId);
    List<Transactions> findBySourceCardNumberAndDestinationCardNumber(Long sourceCardNumber, Long destinationCardNumber);
    List<Transactions> findByType(TransactionType transactionType);
    List<Transactions> findBySourceCardNumberAndDateAndTransactionStatus(Long sourceCard, Date date, TransactionStatus status);
}
