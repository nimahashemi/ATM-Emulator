package com.egs.atmemulator.controller;

import com.egs.atmemulator.dto.CardDTO;
import com.egs.atmemulator.dto.StandardOperationDTO;
import com.egs.atmemulator.dto.TransferDTO;
import com.egs.atmemulator.enums.*;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "/cards", method = RequestMethod.GET, produces = {"application/json"})
    public List<Card> getAllUsers(@RequestParam(value = "account_id", required = false) Long account_id,
                                  @RequestParam(value = "cardNumber", required = false) Long cardNumber,
                                  @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                  @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                  @RequestParam(value = "status", required = false) CardStatus status)  {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(account_id);
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setFrom(from);
        cardDTO.setTo(to);
        cardDTO.setStatus(status);
        return cardService.inquiry(cardDTO);
    }

    @RequestMapping(value = "/card/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public Card findUserById(@PathVariable("id") Long id) {
        return cardService.findById(id);
    }

    @RequestMapping(value = "/card", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Card> add(@Valid @RequestBody Card card) {
        Card result = cardService.add(card);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/card/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody Card card) {
        cardService.update(id, card);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/card/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        cardService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/card/operation/{type}", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Long> operation(@PathVariable("type") TransactionType type,
                                          @Valid @RequestBody StandardOperationDTO standardOperationDTO) {
        Long balance = cardService.standardOperation(standardOperationDTO.getCardNumber(),
                standardOperationDTO.getPinOne(),
                standardOperationDTO.getAmount(),
                type);
        return ResponseEntity.ok(balance);
    }

    @RequestMapping(value = "/card/transfer", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Long> transfer(@Valid @RequestBody TransferDTO transferDTO) {
        Long balance = cardService.transfer(transferDTO.getSrcCard(),
                transferDTO.getDestCard(),
                transferDTO.getPinOne(),
                transferDTO.getPinTwo(),
                transferDTO.getAmount(),
                TransactionType.TRANSFER);
        return ResponseEntity.ok(balance);
    }
}
