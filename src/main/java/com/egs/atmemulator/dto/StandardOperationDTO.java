package com.egs.atmemulator.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StandardOperationDTO {

    @NotNull
    private Long cardNumber;
    @NotNull
    private int pinOne;
    private int amount;
}
