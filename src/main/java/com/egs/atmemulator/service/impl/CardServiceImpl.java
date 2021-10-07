package com.egs.atmemulator.service.impl;

import com.egs.atmemulator.dto.CardDTO;
import com.egs.atmemulator.dto.ResponseDTO;
import com.egs.atmemulator.enums.CardStatus;
import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
import com.egs.atmemulator.exceptions.BadRequestException;
import com.egs.atmemulator.exceptions.NotAcceptableException;
import com.egs.atmemulator.model.Account;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.model.Transactions;
import com.egs.atmemulator.repository.CardRepository;
import com.egs.atmemulator.service.AccountService;
import com.egs.atmemulator.service.CardService;
import com.egs.atmemulator.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    private CardRepository cardRepository;
    private TransactionsService transactionsService;
    private AccountService accountService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           TransactionsService transactionsService,
                           AccountService accountService) {
        this.cardRepository = cardRepository;
        this.transactionsService = transactionsService;
        this.accountService = accountService;
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Long id) {
        Optional<Card> optional = cardRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("Card not found");
        return optional.get();
    }

    @Override
    public Card add(Card object) {
        if (String.valueOf(object.getCardNumber()).length() != 16)
            throw new BadRequestException(112, "Invalid Card Number");
        if (String.valueOf(object.getPinOne()).length() != 4 || (String.valueOf(object.getPinTwo()).length() <= 4 && String.valueOf(object.getPinTwo()).length() >= 6))
            throw new BadRequestException(109, "Invalid pin");

        Card card = cardRepository.save(object);
        return card;
    }

    @Override
    public Card update(Card object) {
        if (String.valueOf(object.getCardNumber()).length() != 16)
            throw new BadRequestException(112, "Invalid Card Number");
        if (String.valueOf(object.getPinOne()).length() != 4 || (String.valueOf(object.getPinTwo()).length() <= 4 && String.valueOf(object.getPinTwo()).length() >= 6))
            throw new BadRequestException(109, "Invalid pin");

        Card card = cardRepository.save(object);
        return card;
    }

    @Override
    public Card update(Long cardId, Card card) {
        if (String.valueOf(card.getCardNumber()).length() != 16)
            throw new BadRequestException(112, "Invalid Card Number");
        if (String.valueOf(card.getPinOne()).length() != 4 || (String.valueOf(card.getPinTwo()).length() <= 4 && String.valueOf(card.getPinTwo()).length() >= 6))
            throw new BadRequestException(109, "Invalid pin");

        Optional<Card> optional = cardRepository.findById(cardId);
        if (!optional.isPresent()) throw new NotAcceptableException("Card not found");

        Card object = cardRepository.save(card);
        return object;
    }

    @Override
    public void delete(Card object) {
        Card card = object;
        card.setStatus(CardStatus.DELETED);
        cardRepository.save(card);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Card> optional = cardRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("Card not found");
        Card card = optional.get();
        card.setStatus(CardStatus.DELETED);
        cardRepository.save(card);
    }

    @Override
    public List<Card> search(Card object) {
        return cardRepository.search(object.getAccountId(),
                object.getCardNumber(),
                object.getExpiryDate(),
                object.getExpiryDate(),
                object.getStatus());
    }

    @Override
    public List<Card> inquiry(CardDTO cardDTO) {
        Account account = accountService.findById(cardDTO.getAccountId());

        return cardRepository.search(account.getId(),
                cardDTO.getCardNumber(),
                cardDTO.getFrom(),
                cardDTO.getTo(),
                cardDTO.getStatus());
    }

    @Override
    public Card LoginByCardNumAndPin(String cardNum, String pin) {
        if (String.valueOf(cardNum).length() != 16)
            throw new BadRequestException(107, "Invalid Card Number");
        if (pin.length() != 4)
            throw new BadRequestException(109, "Invalid pin");

        Card card = null;
        card = cardRepository.findByCardNumberAndPinOneAndStatus(Long.valueOf(cardNum), Integer.valueOf(pin), CardStatus.ACTIVE);
        if (card == null) throw new NotAcceptableException("Card information is invalid.");

        return card;
    }


    @Override
    public ResponseDTO standardOperation(Long cardNumber, int pinOne, int amount, TransactionType type) {
        Long balance = Long.valueOf(0);

        if (String.valueOf(cardNumber).length() != 16)
            throw new BadRequestException(107, "Invalid Card Number");
        if (String.valueOf(pinOne).length() != 4)
            throw new BadRequestException(109, "Invalid pin");
        if (type.equals(TransactionType.TRANSFER))
            throw new BadRequestException(110, "Invalid transaction type");

        Card card = cardRepository.findByCardNumberAndPinOneAndStatus(cardNumber, pinOne, CardStatus.ACTIVE);
        List<Transactions> failedTransactions = transactionsService.findBySourceCardNumberAndDateAndTransactionStatus(cardNumber, new Date(), TransactionStatus.FAILED);
        Transactions transactions = new Transactions();

        if (card != null && failedTransactions.size() <= 3) {
            Account account = accountService.findById(card.getAccountId());

            if (type.equals(TransactionType.BALANCE)) {
                balance = account.getBalance();
                transactions.setType(TransactionType.BALANCE);
            } else if (type.equals(TransactionType.DEPOSIT) && (amount != 0)) {
                if (amount <= 10)
                    throw new BadRequestException(108, "Invalid amount");

                balance = account.getBalance() + Long.valueOf(amount);
                account.setBalance(balance);
                accountService.update(account);
                transactions.setType(TransactionType.DEPOSIT);
            } else if (type.equals(TransactionType.WITHDRAW) && (amount != 0)) {
                if (amount <= 10)
                    throw new BadRequestException(108, "Invalid amount");

                balance = account.getBalance() - Long.valueOf(amount);
                if (balance < 0) {
                    throw new NotAcceptableException("Withdraw limited ...!");
                } else {
                    account.setBalance(balance);
                    accountService.update(account);
                }
                transactions.setType(TransactionType.WITHDRAW);
            } else {
                throw new NotAcceptableException("Transaction Type not allowed");
            }

            transactions.setUserId(account.getUserId());
            transactions.setDate(new Date());
            transactions.setSourceCardNumber(card.getCardNumber());
            transactions.setTransactionStatus(TransactionStatus.SUCCESS);
            transactions.setAmount(Long.valueOf(amount));

        } else {
            card.setStatus(CardStatus.BLOCKED);
            update(card);

            transactions.setSourceCardNumber(cardNumber);
            transactions.setReason("Card or Pin is invalid");
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            throw new NotAcceptableException("Card or Pin is invalid");
        }
        transactionsService.add(transactions);

        ResponseDTO response = new ResponseDTO(balance);
        return response;
    }

    @Override
    public ResponseDTO transfer(Long srcCard, Long destCard, int pinOne, int pinTwo, int amount, TransactionType type) throws ParseException {
        Long srcBalance = Long.valueOf(0);
        Long dstBalance = Long.valueOf(0);

        if (String.valueOf(srcCard).length() != 16 || String.valueOf(destCard).length() != 16)
            throw new BadRequestException(107, "Invalid Card Number");
        if (amount <= 50)
            throw new BadRequestException(108, "Invalid amount");
        if (String.valueOf(pinOne).length() != 4 || (String.valueOf(pinTwo).length() <= 4 && String.valueOf(pinTwo).length() >= 6))
            throw new BadRequestException(109, "Invalid pin");
        if (!type.equals(TransactionType.TRANSFER))
            throw new BadRequestException(110, "Invalid transaction type");

        Card sCard = cardRepository.findByCardNumberAndPinOneAndStatus(srcCard, pinOne, CardStatus.ACTIVE);
        Card dCard = cardRepository.findByCardNumberAndStatus(destCard, CardStatus.ACTIVE);
        List<Transactions> failedTransactions = transactionsService.findBySourceCardNumberAndDateAndTransactionStatus(srcCard, new Date(), TransactionStatus.FAILED);
        Transactions transactions = new Transactions();

        if (sCard != null && dCard != null && failedTransactions.size() <= 3) {
            Account srcAccount = accountService.findById(sCard.getAccountId());
            Account dstAccount = accountService.findById(dCard.getAccountId());

            if (type.equals(TransactionType.TRANSFER)) {
                srcBalance = srcAccount.getBalance() - Long.valueOf(amount);
                dstBalance = dstAccount.getBalance() + Long.valueOf(amount);

                SimpleDateFormat dateFormatStr = new SimpleDateFormat("yyyy-MM-DD");
                String dateStr = dateFormatStr.format(new Date());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
                Date now = dateFormat.parse(dateStr);
                Card secondCheck = cardRepository.findByCardNumberAndPinTwoAndStatusAndExpiryDateLessThan(srcCard, pinTwo, CardStatus.ACTIVE, now);


                if (srcBalance > 0 && secondCheck != null) {

                    srcAccount.setBalance(srcBalance);
                    dstAccount.setBalance(dstBalance);

                    transactions.setUserId(srcAccount.getUserId());
                    transactions.setDate(new Date());
                    transactions.setSourceCardNumber(sCard.getCardNumber());
                    transactions.setDestinationCardNumber(dCard.getCardNumber());
                    transactions.setTransactionStatus(TransactionStatus.SUCCESS);
                    transactions.setAmount(Long.valueOf(amount));

                    accountService.update(srcAccount);
                    accountService.update(dstAccount);

                    transactions.setType(TransactionType.TRANSFER);
                } else {
                    transactions.setSourceCardNumber(srcCard);
                    transactions.setDestinationCardNumber(destCard);
                    transactions.setReason("Balance less than 0 or incorrect pin 2");
                    transactions.setTransactionStatus(TransactionStatus.FAILED);
                    throw new NotAcceptableException("Balance or Pin is invalid");
                }
            } else {
                throw new NotAcceptableException("Transaction Type not allowed");
            }

        } else {
            sCard.setStatus(CardStatus.BLOCKED);
            update(sCard);

            transactions.setSourceCardNumber(srcCard);
            transactions.setDestinationCardNumber(destCard);
            transactions.setReason("Card or Pin is invalid");
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            throw new NotAcceptableException("Card or Pin is invalid");
        }
        transactionsService.add(transactions);
        ResponseDTO response = new ResponseDTO(srcBalance);
        return response;
    }
}
