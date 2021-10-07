package com.egs.atmemulator.controller;

import com.egs.atmemulator.dto.jwt.JwtRequest;
import com.egs.atmemulator.dto.jwt.JwtResponse;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.service.CardService;
import com.egs.atmemulator.utilities.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private CardService cardService;

    @Autowired
    public JwtAuthenticationController (AuthenticationManager authenticationManager,
                                        JwtTokenUtil jwtTokenUtil,
                                        CardService cardService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil = jwtTokenUtil;
        this.cardService = cardService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        final Card card = cardService.LoginByCardNumAndPin(authenticationRequest.getCard(), authenticationRequest.getPin() );
        final String token = jwtTokenUtil.generateToken(authenticationRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
