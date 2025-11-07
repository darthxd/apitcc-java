package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentId(Long studentId);
    List<Grade> findAllByStudentIdAndSubjectId(Long studentId, Long subjectId);
    boolean existsByStudentIdAndSubjectIdAndBimesterAndYear(Long studentId, Long subjectId, Integer bimester, Integer year);
    @Query("""
           SELECT g FROM Grade g
           WHERE g.student.schoolClass.id = :classId
           AND g.bimester = :bimester
           """)
    List<Grade> findBySchoolClassAndBimester(@Param("classId") Long classId, @Param("bimester") Integer bimester);

    @Query("""
           SELECT g FROM Grade g
           WHERE g.student.schoolClass.id = :classId
           """)
    List<Grade> findBySchoolClass(@Param("classId") Long classId);
}
