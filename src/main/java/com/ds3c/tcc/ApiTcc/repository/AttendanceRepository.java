package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByDateAndSchoolClassId(LocalDate date, Long id);
    boolean existsByDateAndSchoolClassIdAndTeacherId(LocalDate date, Long schoolClassId, Long teacherId);
    List<Attendance> findAllBySchoolClassId(Long schoolClassId);
    List<Attendance> findAllByStudentId(Long studentId);

    List<Attendance> findAllBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId);
    List<Attendance> findAllByStudentIdAndSubjectId(Long studentId, Long subjectId);

    @Query("select count(a) from Attendance a where a.schoolClass.id = :schoolClassId and a.subject.id = :subjectId")
    long countClassesGivenForClassAndSubject(Long schoolClassId, Long subjectId);

    @Query("select count(a) from Attendance a where a.student.id = :studentId and a.subject.id = :subjectId and a.present = true")
    long countStudentPresentForSubject(Long studentId, Long subjectId);
}
