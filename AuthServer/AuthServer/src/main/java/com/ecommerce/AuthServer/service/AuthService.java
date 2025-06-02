package com.ecommerce.AuthServer.service;


import com.ecommerce.AuthServer.beans.Credential;
import com.ecommerce.AuthServer.dao.CredentialsRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    CredentialsRepoJPA credentialRepo;

    @Autowired
    TokenService tokenService;

    public String signupService(Credential cred)
    {
        try{
            if(credentialRepo.existsByEmail(cred.getEmail()))
            {
                return "Already signed Up!";
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(cred.getPassword());
            cred.setPassword(encodedPassword);
        credentialRepo.save(cred);
        return "SignedUp successfully!";
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public String[] loginService(Credential cred) {
        Credential original = null;
        if (cred.getEmail() == "") {
            original = credentialRepo.findByPhone(cred.getPhone()).stream().findFirst().get();
        }
        if (cred.getPhone() == "")
        {
            original = credentialRepo.findByEmail(cred.getEmail());
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(original != null && passwordEncoder.matches(cred.getPassword(),original.getPassword()))
        {
                String[] tokenValue = tokenService.createAndSaveToken(original.getId(), original.getRole());
                return tokenValue;
        }


        return null;

    }

    public String validateTokenService(String token)
    {
        if(tokenService.validate(token))
        {
            return "VALID";
        }
        return null;
    }


    public void removeToken(String token) {
        tokenService.removeToken(token);
    }
}
