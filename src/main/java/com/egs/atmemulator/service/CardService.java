package com.egs.atmemulator.service;

import com.egs.atmemulator.dto.CardDTO;
import com.egs.atmemulator.dto.ResponseDTO;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.model.User;

import java.text.ParseException;
import java.util.List;

public interface CardService extends CrudService<Card, Long> {

    ResponseDTO standardOperation(Long cardNumber, int pinOne, int amount, TransactionType type);
    ResponseDTO transfer(Long srcCard, Long destCard, int pinOne, int pinTwo, int amount, TransactionType type) throws ParseException;
    Card update(Long cardId, Card card);
    List<Card> inquiry(CardDTO cardDTO);
    Card LoginByCardNumAndPin(String cardNum, String pin);

}
