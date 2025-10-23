package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
