package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentId(Long studentId);
    List<Grade> findAllByStudentIdAndSubjectId(Long studentId, Long subjectId);
    List<Grade> findAllBySubjectId(Long subjectId);
}
