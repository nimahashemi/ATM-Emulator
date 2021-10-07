package com.egs.atmemulator.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TransferDTO {

    @NotNull
    private Long srcCard;
    @NotNull
    private Long destCard;
    @NotNull
    private int pinOne;
    @NotNull
    private int pinTwo;
    @NotNull
    private int amount;

}
