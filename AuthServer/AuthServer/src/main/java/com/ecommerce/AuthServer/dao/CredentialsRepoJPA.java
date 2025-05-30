package com.ecommerce.AuthServer.dao;

import com.ecommerce.AuthServer.beans.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialsRepoJPA extends JpaRepository<Credential, Long> {
    boolean existsByEmail(String email);
    List<Credential> findByPhone(String phone);
    Credential findByEmail(String email);
}
