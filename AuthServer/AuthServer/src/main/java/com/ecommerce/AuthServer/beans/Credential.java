package com.ecommerce.AuthServer.beans;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", length = 160)
    private String email;

    @Column(name = "phone" , length = 14)
    private String phone;

    @Column(name = "password" , length = 300)
    private String password;

}
