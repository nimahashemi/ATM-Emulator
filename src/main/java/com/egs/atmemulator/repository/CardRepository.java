package com.egs.atmemulator.repository;

import com.egs.atmemulator.enums.AccountStatus;
import com.egs.atmemulator.enums.AccountType;
import com.egs.atmemulator.enums.CardStatus;
import com.egs.atmemulator.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE (?1 is null or c.accountId = ?1) " +
            "and (?2 is null or c.cardNumber = ?2) " +
            "and (?3 is null or c.expiryDate >= ?3) " +
            "and (?4 is null or c.expiryDate <= ?4) " +
            "and (?5 is null or c.status = ?5) ")
    List<Card> search(Long account,
                      Long cardNumber,
                      Date from,
                      Date to,
                      CardStatus status);

    Card findByCardNumberAndPinOneAndStatus(Long cardNumber, int pinOne, CardStatus status);
    Card findByCardNumberAndPinTwoAndStatus(Long cardNumber, int pinTwo, CardStatus status);
    Card findByCardNumberAndStatus(Long cardNumber, CardStatus status);

}
