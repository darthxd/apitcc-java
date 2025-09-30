package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.StudentPresenceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresenceLogRepository extends JpaRepository<StudentPresenceLog, Long> {
    Optional<StudentPresenceLog> findByStudentIdAndDate(Long studentId, LocalDate date);
    List<StudentPresenceLog> findAllByStudentId(Long studentId);
    List<StudentPresenceLog> findAllByDateEquals(LocalDate date);
}
