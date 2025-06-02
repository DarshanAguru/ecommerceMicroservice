package com.ecommerce.AuthServer.controller;


import com.ecommerce.AuthServer.beans.Credential;
import com.ecommerce.AuthServer.service.AuthService;
import com.ecommerce.AuthServer.views.CredentialView;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody CredentialView credView)
    {
        try {
            Credential cred = new Credential();
            cred.setEmail(credView.getEmail());
            cred.setPhone(credView.getPhone());
            cred.setPassword(credView.getPassword());
            cred.setRole(credView.getRole());
            String msg = authService.signupService(cred);
            if(msg != null)
            {
                if(msg.equalsIgnoreCase("Already signed Up!"))
                {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
                }
                return ResponseEntity.status(HttpStatus.CREATED).body(msg);

            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        catch(Exception e)
        {

            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialView credView, HttpServletResponse response)
    {
        try{

            Credential cred = new Credential();
            cred.setEmail(credView.getEmail());
            cred.setPassword(credView.getPassword());
            cred.setPhone(credView.getPhone());
            String[] token = authService.loginService(cred);
            if(token != null)
            {
                response.addHeader("Authorization", "Bearer "+token[0]);
                if(token[1].equalsIgnoreCase("conflict"))
                {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                return ResponseEntity.status(HttpStatus.OK).build();

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token)
    {
        String msg = null;
        if(token != null)
        {
            msg = authService.validateTokenService(token.substring(7));

            authService.removeToken(token.substring(7));
        }
        if(msg == null)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

}
