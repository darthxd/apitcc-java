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
import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceMapper {
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final AttendanceService attendanceService;

    @Autowired
    @Lazy
    public AttendanceMapper(StudentService studentService,
                            SchoolClassService schoolClassService,
                            TeacherService teacherService,
                            AttendanceService attendanceService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.attendanceService = attendanceService;
    }

    public Attendance toModel(AttendanceRequestDTO dto) {
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
        attendance.setPresent(dto.getPresent());
        return attendance;
    }

    public AttendanceResponseDTO toDTO(Attendance attendance) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setStudentId(attendance.getStudent().getId());
        dto.setSchoolClassId(attendance.getSchoolClass().getId());
        dto.setTeacherId(attendance.getTeacher().getId());
        dto.setPresent(attendance.getPresent());
        return dto;
    }

    public List<AttendanceResponseDTO> toListDTO(List<Attendance> attendanceList) {
        List<AttendanceResponseDTO> dtoList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            dtoList.add(toDTO(attendance));
        }
        return dtoList;
    }

    public Attendance updateModelFromDTO(AttendanceRequestDTO dto, Long id) {
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
        if (dto.getPresent() != null) {
            attendance.setPresent(dto.getPresent());
        }
        return attendance;
    }
}
