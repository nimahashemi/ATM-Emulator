package com.egs.atmemulator.service;

import com.egs.atmemulator.dto.CardDTO;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.model.User;

import java.util.List;

public interface CardService extends CrudService<Card, Long> {

    Long standardOperation(Long cardNumber, int pinOne, int amount, TransactionType type);
    Long transfer(Long srcCard, Long destCard, int pinOne, int pinTwo, int amount, TransactionType type);
    Card update(Long cardId, Card card);
    List<Card> inquiry(CardDTO cardDTO);
    Card LoginByCardNumAndPin(String cardNum, String pin);

}
