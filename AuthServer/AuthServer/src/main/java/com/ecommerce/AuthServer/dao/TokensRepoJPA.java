package com.ecommerce.AuthServer.dao;

import com.ecommerce.AuthServer.beans.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokensRepoJPA extends JpaRepository<Token, String> {

    boolean existsByUserId(String userId);

    Token findByUserId(String userId);
    Token findByToken(String token);
}

