package com.ecommerce.AuthServer.service;

import com.ecommerce.AuthServer.beans.Token;
import com.ecommerce.AuthServer.dao.TokensRepoJPA;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenService {

    private final String secret = "JFHRGYRGAdnjkfhagRSGkajkshdgasdfAJEAdndg";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    private final long expireTime = 24 * 60 * 60 * 1000;
    @Autowired
    TokensRepoJPA tokensRepo;



    private String generateToken(String username) {

        return  Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String createAndSaveToken(UUID id)
    {
        try {
            String userId = String.valueOf(id);
            String tokenValue = generateToken(userId);
            if(tokensRepo.existsByUserId(userId))
            {

                String tokn =  tokensRepo.findByUserId(userId).getToken();
                if(validate(tokn))
                {

                    return tokn;
                }
                else{

                    removeToken(tokn);
                }
            }

            Token token = new Token();
            token.setToken(tokenValue);
            token.setUserId(userId);
            tokensRepo.save(token);
            return tokenValue;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean validate(String token)
    {

        try {
            Token original = tokensRepo.findByToken(token);

            if(original == null)
            {
                return false;
            }
            String userId = original.getUserId();
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            if (!(claims.getSubject().equals(userId))) {

                return false;
            }
            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {

                return false;
            }
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void removeToken(String token)
    {
        try {
            tokensRepo.deleteById(token);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


}
