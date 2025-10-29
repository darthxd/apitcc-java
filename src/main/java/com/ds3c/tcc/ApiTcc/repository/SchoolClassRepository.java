package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findByCourseAndGradeAndShift(CoursesEnum course, GradesEnum grade, ShiftsEnum shift);

    @Query("""
        SELECT sc FROM SchoolClass sc
        WHERE sc.grade = :grade
          AND sc.course = :course
          AND sc.shift = :shift
          AND sc.studentsCount < sc.studentsLimit
        ORDER BY sc.id ASC
    """)
    Optional<SchoolClass> findAvailableClass(
            @Param("grade") GradesEnum grade,
            @Param("course") CoursesEnum course,
            @Param("shift") ShiftsEnum shift
    );
}
