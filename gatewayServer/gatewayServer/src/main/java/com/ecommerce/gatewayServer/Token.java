package com.ecommerce.gatewayServer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Entity
@Table(name = "tokens")
public class Token {

    Token(){}
    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "userid")
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Id
    @Column(name ="token", length = 200, nullable = false)
    private String token;


    Token(String userId , String token)
    {
        this.token = token;
        this.userId = userId;
    }

    @Column(name="role", length=20)
    private String role;
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
