package com.egs.atmemulator.dto;

import com.egs.atmemulator.enums.CardStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CardDTO {

    private Long accountId;
    private Long cardNumber;
    private Date from;
    private Date to;
    private CardStatus status;
}
