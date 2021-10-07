package com.egs.atmemulator.configuration;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.egs.atmemulator.dto.jwt.JwtRequest;
import com.egs.atmemulator.dto.jwt.Principal;
import com.egs.atmemulator.model.Card;
import com.egs.atmemulator.service.CardService;
import com.egs.atmemulator.utilities.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private CardService cardService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(CardService cardService,
                            JwtTokenUtil jwtTokenUtil) {
        this.cardService = cardService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Filter All Request based on API Call
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String card = null;
        String pin = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                card = jwtTokenUtil.getValueFromToken(jwtToken, "card");
                pin = jwtTokenUtil.getValueFromToken(jwtToken, "pin");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (card != null && pin != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            Card cardObject = cardService.LoginByCardNumAndPin(card, pin);
            JwtRequest jwtRequest = new JwtRequest(String.valueOf(cardObject.getCardNumber()), String.valueOf(cardObject.getPinOne()));

            if (jwtTokenUtil.validateToken(jwtToken, jwtRequest)) {
                Principal principal = new Principal(card, pin);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

