package com.newspaper.journal.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    String secretKey="";

    public JwtService(){
        try {
            KeyGenerator keyGenerated = KeyGenerator.getInstance("HmacSHA256");
            SecretKey keyGenString = keyGenerated.generateKey();
            secretKey = Base64.getEncoder().encodeToString(keyGenString.getEncoded());
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error generating secret key: " + e.getMessage(), e);
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, String username){
        String extractedUsername = extractClaims(token).getSubject();
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
