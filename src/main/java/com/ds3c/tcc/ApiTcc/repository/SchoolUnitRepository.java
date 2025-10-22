package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolUnitRepository extends JpaRepository<SchoolUnit, Long> {
    Optional<SchoolUnit> findByNameIgnoreCase(String name);
}
