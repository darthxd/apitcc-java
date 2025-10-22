package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Attendance;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AttendanceMapper {
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final AttendanceService attendanceService;

    @Autowired
    @Lazy
    public AttendanceMapper(
            StudentService studentService,
            SchoolClassService schoolClassService,
            TeacherService teacherService,
            AttendanceService attendanceService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.attendanceService = attendanceService;
    }

    public Attendance toEntity(AttendanceRequestDTO dto) {
        Student student = studentService
                .getStudentById(dto.getStudentId());
        SchoolClass schoolClass = schoolClassService
                .getSchoolClassById(dto.getSchoolClassId());
        Teacher teacher = teacherService
                .getTeacherById(dto.getTeacherId());
        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        attendance.setStudent(student);
        attendance.setSchoolClass(schoolClass);
        attendance.setTeacher(teacher);
        attendance.setIsInSchool(dto.getIsInSchool());
        attendance.setPresent(dto.getPresent());
        return attendance;
    }

    public AttendanceResponseDTO toDTO(Attendance attendance) {
        return new AttendanceResponseDTO(
                attendance.getId(),
                attendance.getDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                attendance.getStudent().getId(),
                attendance.getSchoolClass().getId(),
                attendance.getTeacher().getId(),
                attendance.getIsInSchool(),
                attendance.getPresent()
        );
    }

    public Attendance updateEntityFromDTO(AttendanceRequestDTO dto, Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (StringUtils.hasText(dto.getDate())) {
            attendance.setDate(LocalDate.parse(dto.getDate()));
        }
        if (dto.getStudentId() != null) {
            attendance.setStudent(
                    studentService.getStudentById(dto.getStudentId())
            );
        }
        if (dto.getSchoolClassId() != null) {
            attendance.setSchoolClass(
                    schoolClassService.getSchoolClassById(dto.getSchoolClassId())
            );
        }
        if (dto.getTeacherId() != null) {
            attendance.setTeacher(
                    teacherService.getTeacherById(dto.getTeacherId())
            );
        }
        if (dto.getIsInSchool() != null) {
            attendance.setIsInSchool(dto.getIsInSchool());
        }
        if (dto.getPresent() != null) {
            attendance.setPresent(dto.getPresent());
        }
        return attendance;
    }
}
