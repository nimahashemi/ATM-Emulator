package com.egs.atmemulator.model;

import com.egs.atmemulator.enums.TransactionStatus;
import com.egs.atmemulator.enums.TransactionType;
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
@Table(name = "Transactions")
public class Transactions {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "type")
    private TransactionType type;

    @NotNull
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @NotNull
    @Column(name = "source_card_number")
    @Size(min = 16, max = 16)
    private Long sourceCardNumber;

    @Column(name = "destination_card_number")
    @Size(min = 16, max = 16)
    private Long destinationCardNumber;

    @Column(name = "amount")
    private Long amount;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    private String reason;
}
