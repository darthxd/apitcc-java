package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StudentMapper {
    private final SchoolClassService schoolClassService;
    private final UserService userService;
    private final StudentService studentService;
    private final SchoolClassMapper schoolClassMapper;

    @Autowired
    @Lazy
    public StudentMapper(
            SchoolClassService schoolClassService,
            UserService userService,
            StudentService studentService,
            SchoolClassMapper schoolClassMapper) {
        this.schoolClassService = schoolClassService;
        this.userService = userService;
        this.studentService = studentService;
        this.schoolClassMapper = schoolClassMapper;
    }

    public Student toEntity(StudentRequestDTO studentRequestDTO, Long userId) {
        SchoolClass schoolClass = schoolClassService
                .getById(studentRequestDTO.getSchoolClassId());
        Student student = new Student();

        student.setName(studentRequestDTO.getName());
        student.setRa(studentRequestDTO.getRa());
        student.setRm(studentRequestDTO.getRm());
        student.setCpf(studentRequestDTO.getCpf());
        student.setPhone(studentRequestDTO.getPhone());
        student.setEmail(studentRequestDTO.getEmail());
        student.setSchoolClass(schoolClass);
        student.setBirthdate(LocalDate.parse(
                studentRequestDTO.getBirthdate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        student.setPhoto(studentRequestDTO.getPhoto());
        student.setUserId(userId);
        student.setSendNotification(studentRequestDTO.getSendNotification());

        return student;
    }

    public StudentResponseDTO toDTO(Student student) {
        SchoolClassResumeDTO schoolClass = schoolClassMapper.toResumeDTO(student.getSchoolClass());
        User user = userService.getById(student.getUserId());

        return new StudentResponseDTO(
                student.getId(),
                user.getUsername(),
                user.getPassword(),
                student.getName(),
                student.getRa(),
                student.getRm(),
                student.getCpf(),
                student.getPhone(),
                student.getEmail(),
                schoolClass,
                student.getBirthdate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                student.getPhoto(),
                student.getSendNotification(),
                student.getBiometry(),
                student.getInschool(),
                user.getSchoolUnit().getId()
        );
    }

    public Student updateEntityFromDTO(StudentRequestDTO dto, Long id) {
        Student student = studentService.getById(id);
        if (StringUtils.hasText(dto.getUsername())
                || StringUtils.hasText(dto.getPassword())) {
            userService.update(dto, student.getUserId());
        }
        if (StringUtils.hasText(dto.getName())) {
            student.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getRa())) {
            student.setRa(dto.getRa());
        }
        if (StringUtils.hasText(dto.getRm())) {
            student.setRm(dto.getRm());
        }
        if (StringUtils.hasText(dto.getCpf())) {
            student.setCpf(dto.getCpf());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            student.setPhone(dto.getPhone());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            student.setEmail(dto.getEmail());
        }
        if (dto.getSchoolClassId() != null) {
            student.setSchoolClass(
                    schoolClassService.getById(dto.getSchoolClassId())
            );
        }
        if (dto.getBirthdate() != null) {
            if (LocalDate.parse(dto.getBirthdate()).isBefore(LocalDate.now())) {
                student.setBirthdate(LocalDate.parse(dto.getBirthdate()));
            }
        }
        if (StringUtils.hasText(dto.getPhoto())) {
            student.setPhoto(dto.getPhoto());
        }
        if (dto.getSendNotification() != null) {
            student.setSendNotification(dto.getSendNotification());
        }
        return student;
    }
}
