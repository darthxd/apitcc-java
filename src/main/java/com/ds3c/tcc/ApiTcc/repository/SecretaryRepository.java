package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    Optional<Secretary> findByUsername(String username);
}
