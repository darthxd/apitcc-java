package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.enums.DayOfWeekEnum;
import com.ds3c.tcc.ApiTcc.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findAllBySchoolClassId(Long schoolClassId);
    List<ClassSchedule> findAllBySchoolClassIdAndDayOfWeek(Long schoolClassId, DayOfWeekEnum dayOfWeek);
    List<ClassSchedule> findAllByTeacherId(Long teacherId);
}
