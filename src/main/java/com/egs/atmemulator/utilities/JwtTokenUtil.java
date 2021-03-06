package com.egs.atmemulator.utilities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.egs.atmemulator.dto.jwt.JwtRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -1L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Get value from token based on key
     *
     * @param token
     * @param key
     * @return
     */
    //retrieve username from jwt token
    public String getValueFromToken(String token, String key) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(key);

    }

    /**
     * retrieve expiration date from jwt token
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * For retrieveing any information from token we will need the secret key
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the token has expired
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token for user
     *
     * @param jwtRequest
     * @return
     */
    public String generateToken(JwtRequest jwtRequest) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, jwtRequest.getCard(), jwtRequest.getPin());
    }

    /**
     * While creating the token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * Compaction of the JWT to a URL-safe string
     *
     * @param claims
     * @param card
     * @param pin
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String card, String pin) {

        return Jwts.builder().setClaims(claims).setSubject(card).setSubject(pin).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Validate Token
     *
     * @param token
     * @param jwtRequest
     * @return
     */
    public Boolean validateToken(String token, JwtRequest jwtRequest) {
        boolean isValid = false;
        final String card = getValueFromToken(token, "card");
        final String pin = getValueFromToken(token, "pin");
        if (card.equals(jwtRequest.getCard()) && pin.equals(jwtRequest.getPin()) && !isTokenExpired(token)) isValid = true;
        return isValid;
    }
}
