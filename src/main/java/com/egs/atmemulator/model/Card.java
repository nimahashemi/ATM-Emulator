package com.egs.atmemulator.model;

import com.egs.atmemulator.enums.BlockedReason;
import com.egs.atmemulator.enums.CardStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_id")
    private Long accountId;

    @NotNull
    @Column(name = "card_number", unique = true)
    @Size(min = 16, max = 16, message = "Card number must be 16 digit")
    private Long cardNumber;

    @NotNull
    @Column(name = "pin_one")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    private int pinOne;

    @NotNull
    @Column(name = "pin_two")
    @Size(min = 6, max = 6, message = "PIN must be 6 digits")
    private int pinTwo;

    @NotNull
    @Column(name = "cvv")
    @Size(min = 3, max = 4, message = "PIN must be 6 digits")
    private int cvv;

    @NotNull
    @Column(name = "expiry_date")
    @DateTimeFormat(pattern = "yy/MM")
    private Date expiryDate;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "blocked_reason")
    @Enumerated(EnumType.STRING)
    private BlockedReason blockedReason;

}
