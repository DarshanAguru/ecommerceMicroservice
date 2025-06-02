package com.ecommerce.gatewayServer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepoJPA extends JpaRepository<Token, String> {

    boolean existsByUserId(String userId);

    Token findByUserId(String userId);
    Token findByToken(String token);
}
