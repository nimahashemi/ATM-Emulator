package com.egs.atmemulator.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String card;
    private String pin;

}
