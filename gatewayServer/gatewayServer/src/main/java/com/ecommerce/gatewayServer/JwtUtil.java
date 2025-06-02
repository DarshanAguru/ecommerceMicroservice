package com.ecommerce.gatewayServer;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtil {


    private TokensRepoJPA tokenRepo;
    JwtUtil(TokensRepoJPA tokenRepo)
    {
        this.tokenRepo = tokenRepo;
    }
    private final String secret = "JFHRGYRGAdnjkfhagRSGkajkshdgasdfAJEAdndg";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));


    public boolean validateRole(String token, String role)
    {
        try{

            Token original = tokenRepo.findByToken(token);
            return original.getRole().equalsIgnoreCase(role);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean validate(String token)
    {

        try {

            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            Token original = tokenRepo.findByToken(token);
            if(original == null)
            {
                return false;
            }
            if(!claims.getSubject().equalsIgnoreCase(original.getUserId()))
            {
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

}
