package com.ecommerce.gatewayServer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepoJPA extends JpaRepository<Token, String> {

    Token findByToken(String token);
}
