package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.ClassSchedule.ClassScheduleRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ClassSchedule.ClassScheduleResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.DayOfWeekEnum;
import com.ds3c.tcc.ApiTcc.model.ClassSchedule;
import com.ds3c.tcc.ApiTcc.service.ClassScheduleService;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.SchoolSubjectService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class ClassScheduleMapper {
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final SchoolSubjectService schoolSubjectService;
    private final ClassScheduleService classScheduleService;

    public ClassSchedule toEntity(ClassScheduleRequestDTO dto) {
        ClassSchedule schedule = new ClassSchedule();

        schedule.setSchoolClass(schoolClassService.findById(dto.getSchoolClassId()));
        schedule.setTeacher(teacherService.findById(dto.getTeacherId()));
        schedule.setSubject(schoolSubjectService.findById(dto.getSubjectId()));
        schedule.setDayOfWeek(DayOfWeekEnum.valueOf(dto.getDayOfWeek().toUpperCase()));
        schedule.setStartTime(LocalTime.parse(dto.getStartTime()));
        schedule.setEndTime(LocalTime.parse(dto.getEndTime()));

        return schedule;
    }

    public ClassScheduleResponseDTO toDTO(ClassSchedule schedule) {
        return new ClassScheduleResponseDTO(
                schedule.getId(),
                schedule.getSchoolClass().getId(),
                schedule.getTeacher().getId(),
                schedule.getSubject().getId(),
                schedule.getDayOfWeek().name(),
                schedule.getStartTime().toString(),
                schedule.getEndTime().toString()
        );
    }

    public ClassSchedule updateEntityFromDTO(ClassScheduleRequestDTO dto, Long id) {
        ClassSchedule schedule = classScheduleService.findById(id);
        if (dto.getSchoolClassId() != null) {
            schedule.setSchoolClass(
                    schoolClassService.findById(dto.getSchoolClassId())
            );
        }
        if (dto.getTeacherId() != null) {
            schedule.setTeacher(
                    teacherService.findById(dto.getTeacherId())
            );
        }
        if (dto.getSubjectId() != null) {
            schedule.setSubject(
                    schoolSubjectService.findById(dto.getSubjectId())
            );
        }
        if (StringUtils.hasText(dto.getDayOfWeek())) {
            try {
                schedule.setDayOfWeek(DayOfWeekEnum.valueOf(dto.getDayOfWeek().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("The day of week Enum value is incorrect.");
            }
        }
        if (StringUtils.hasText(dto.getStartTime())) {
            schedule.setStartTime(LocalTime.parse(dto.getStartTime()));
        }
        if (StringUtils.hasText(dto.getEndTime())) {
            schedule.setEndTime(LocalTime.parse(dto.getEndTime()));
        }
        return schedule;
    }
}
