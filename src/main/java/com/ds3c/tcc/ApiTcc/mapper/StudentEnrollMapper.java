package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.*;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.StudentEnroll;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StudentEnrollMapper {

    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final SchoolUnitService schoolUnitService;
    private final PasswordEncoder passwordEncoder;
    private final SchoolClassMapper schoolClassMapper;

    public StudentEnrollMapper(
            StudentService studentService,
            SchoolClassService schoolClassService,
            SchoolUnitService schoolUnitService,
            PasswordEncoder passwordEncoder,
            SchoolClassMapper schoolClassMapper) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.schoolUnitService = schoolUnitService;
        this.passwordEncoder = passwordEncoder;
        this.schoolClassMapper = schoolClassMapper;
    }

    public StudentEnroll toEntity(StudentEnrollRequestDTO dto) {
        StudentEnroll studentEnroll = new StudentEnroll();
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());

        studentEnroll.setName(dto.getName());
        studentEnroll.setRm(studentService.findMaxRm() + 1);
        studentEnroll.setRa(dto.getRa());
        studentEnroll.setCpf(dto.getCpf());
        studentEnroll.setPhone(dto.getPhone());
        studentEnroll.setEmail(dto.getEmail());
        try {
            studentEnroll.setGradeYear(YearsEnum.valueOf(dto.getGradeYear().toUpperCase()));
            studentEnroll.setCourse(CoursesEnum.valueOf(dto.getCourse().toUpperCase()));
            studentEnroll.setShift(ShiftsEnum.valueOf(dto.getShift().toUpperCase()));
            studentEnroll.setSchoolClass(schoolClassService.findAvailable(
                    dto.getGradeYear(), dto.getCourse(), dto.getShift()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "One or more of the Enum values passed are incorrect (GradeYear, Course and/or Shift).");
        }
        studentEnroll.setBirthdate(LocalDate.parse(dto.getBirthdate()));
        studentEnroll.setAddress(dto.getAddress());
        studentEnroll.setSchoolUnit(unit);
        studentEnroll.setStatus(StatusEnum.INACTIVE);
        studentEnroll.setCreatedAt(dto.getCreatedAt());

        return studentEnroll;
    }

    public StudentEnrollResponseDTO toDTO(StudentEnroll studentEnroll) {
        return new StudentEnrollResponseDTO(
                studentEnroll.getId(),
                studentEnroll.getName(),
                studentEnroll.getRm().toString(),
                studentEnroll.getRa(),
                studentEnroll.getCpf(),
                studentEnroll.getPhone(),
                studentEnroll.getEmail(),
                schoolClassMapper.toDTO(studentEnroll.getSchoolClass()),
                studentEnroll.getBirthdate().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                studentEnroll.getAddress(),
                studentEnroll.getPhotoUrl(),
                studentEnroll.getSchoolUnit().getId(),
                studentEnroll.getStatus().name(),
                studentEnroll.getCreatedAt().toString()
        );
    }

    public StudentEnroll updateEntityFromDTO(StudentEnrollRequestDTO dto, Long id) {
        StudentEnroll studentEnroll = studentService.findEnrollById(id);

        if (StringUtils.hasText(dto.getName())) {
            studentEnroll.setName(dto.getName());
        }
        if (dto.getRa() != null) {
            studentEnroll.setRa(dto.getRa());
        }
        if (StringUtils.hasText(dto.getCpf())) {
            studentEnroll.setCpf(dto.getCpf());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            studentEnroll.setPhone(dto.getPhone());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            studentEnroll.setEmail(dto.getEmail());
        }
        if (dto.getGradeYear() != null) {
            try {
                studentEnroll.setGradeYear(YearsEnum.valueOf(dto.getGradeYear().toUpperCase()));
                studentEnroll.setSchoolClass(schoolClassService.findAvailable(
                        dto.getGradeYear(), dto.getCourse(), dto.getShift()
                ));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The grade year with name "+dto.getGradeYear()+" does not exist.");
            }
        }
        if (dto.getCourse() != null) {
            try {
                studentEnroll.setCourse(CoursesEnum.valueOf(dto.getCourse().toUpperCase()));
                studentEnroll.setSchoolClass(schoolClassService.findAvailable(
                        dto.getGradeYear(), dto.getCourse(), dto.getShift()
                ));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The course with name "+dto.getCourse()+" does not exist.");
            }
        }
        if (dto.getShift() != null) {
            try {
                studentEnroll.setShift(ShiftsEnum.valueOf(dto.getShift().toUpperCase()));
                studentEnroll.setSchoolClass(schoolClassService.findAvailable(
                        dto.getGradeYear(), dto.getCourse(), dto.getShift()
                ));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The shift "+dto.getShift()+" does not exist.");
            }
        }
        if (StringUtils.hasText(dto.getBirthdate())) {
            studentEnroll.setBirthdate(LocalDate.parse(dto.getBirthdate()));
        }
        if (StringUtils.hasText(dto.getAddress())) {
            studentEnroll.setAddress(dto.getAddress());
        }
        return studentEnroll;
    }

    private String generatePassword(StudentEnroll studentEnroll) {
        return studentEnroll.getName().split(" ")[0].toLowerCase()+studentEnroll.getRm();
    }

    public Student toStudent(StudentEnroll studentEnroll) {
        Student student = new Student();

        student.setUsername(studentEnroll.getRm().toString());
        student.setPassword(passwordEncoder.encode(generatePassword(studentEnroll)));
        student.setRole(RolesEnum.ROLE_STUDENT);
        student.setSchoolUnit(studentEnroll.getSchoolUnit());
        student.setStatus(studentEnroll.getStatus());

        student.setName(studentEnroll.getName());
        student.setRm(studentEnroll.getRm());
        student.setRa(studentEnroll.getRa());
        student.setCpf(studentEnroll.getCpf());
        student.setPhone(studentEnroll.getPhone());
        student.setEmail(studentEnroll.getEmail());
        student.setSchoolClass(studentEnroll.getSchoolClass());
        student.setBirthdate(studentEnroll.getBirthdate());
        student.setAddress(studentEnroll.getAddress());
        student.setPhotoUrl(studentEnroll.getPhotoUrl());

        student.setEnroll(studentEnroll);

        return student;
    }
}
