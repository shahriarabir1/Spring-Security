package com.App.SpringSecurity.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtService {
    public String secretKey="";

    public JwtService(){

        try {
            KeyGenerator  keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=keygen.generateKey();
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String getToken(String username){
        Map<String, Objects> claim=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*10))
                .and().signWith(getKey())
                .compact();
    }
    private Key getKey(){

        byte[] keyB= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyB);
    }
}
