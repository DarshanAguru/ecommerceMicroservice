package com.ecommerce.AuthServer.beans;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tokens")
public class Token {

    @Column(name = "userid")
    private String userId;

    @Id
    @Column(name ="token", length = 200, nullable = false)
    private String token;

    @Column(name="role", length=20)
    private String  role;



}
