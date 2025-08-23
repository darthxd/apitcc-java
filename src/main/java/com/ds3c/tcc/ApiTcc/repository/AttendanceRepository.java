package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByDateAndSchoolClassId(LocalDate date, Long id);
    boolean existsByDateAndSchoolClassIdAndTeacherId(LocalDate date, Long schoolClassId, Long teacherId);
    List<Attendance> findAllBySchoolClassId(Long schoolClassId);
    List<Attendance> findAllByStudentId(Long studentId);
}
