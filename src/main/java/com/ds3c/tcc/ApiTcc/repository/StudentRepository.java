package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import com.ds3c.tcc.ApiTcc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllBySchoolClassId(Long schoolClassId);
    Optional<Student> findByUsername(String username);
    Optional<Student> findByEnrollId(Long enrollId);

    List<Student> findAllByStatus(StatusEnum status);

    @Query("SELECT MAX(s.rm) FROM Student s")
    Optional<Integer> findMaxRm();
}
