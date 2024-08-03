package com.example.e_commerce.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.e_commerce.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String KEY=
    "13168e77f9b91dcf9ef55272facc3da18f0195f124ab0e1b90b5cbe8247e3034";

    public String extractUsername(String token){
        return extractClaims(token, claims->claims.getSubject());
    }


    public boolean isValid(String token,UserDetails user){
        String username=extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
        

    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

   
    private Date extractExpiration(String token){

        return extractClaims(token, claims->claims.getExpiration());
    }
    public <T> T extractClaims(String token, Function<Claims,T> resolver){

        Claims claims=extractAllClaims(token);
        
        

        return resolver.apply(claims);
        
    }
    

    private Claims extractAllClaims(String token){
        return Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String generateToken(User user){
        String token=Jwts.builder().subject(user.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
        .signWith(getSignKey()).compact();

        return token;
    }
    private SecretKey getSignKey(){
        byte[] keyBytes=Decoders.BASE64URL.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
        
    }
}
