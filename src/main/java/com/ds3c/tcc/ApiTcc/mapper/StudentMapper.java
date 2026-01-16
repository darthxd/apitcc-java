package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final SchoolClassService schoolClassService;
    private final SchoolClassMapper schoolClassMapper;
    private final SchoolUnitService schoolUnitService;
    private final PasswordEncoder passwordEncoder;

    public Student toEntity(StudentRequestDTO dto) {
        SchoolClass schoolClass = schoolClassService
                .findById(dto.getSchoolClassId());
        SchoolUnit unit = schoolUnitService
                .findById(dto.getUnitId());
        Student student = new Student();

        // user
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setRole(RolesEnum.ROLE_STUDENT);
        student.setSchoolUnit(unit);

        // student
        student.setName(dto.getName());
        student.setRa(dto.getRa());
        student.setRm(dto.getRm());
        student.setCpf(dto.getCpf());
        student.setPhone(dto.getPhone());
        student.setEmail(dto.getEmail());
        student.setSchoolClass(schoolClass);
        student.setBirthdate(LocalDate.parse(
                dto.getBirthdate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        student.setPhotoUrl(dto.getPhoto());
        student.setSendNotification(dto.getSendNotification());

        return student;
    }

    public StudentResponseDTO toDTO(Student student) {
        SchoolClassResponseDTO schoolClass = schoolClassMapper
                .toDTO(student.getSchoolClass());

        return new StudentResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getRa(),
                student.getRm().toString(),
                student.getCpf(),
                student.getPhone(),
                student.getEmail(),
                schoolClass,
                student.getBirthdate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                student.getAddress(),
                student.getPhotoUrl(),
                student.getSendNotification(),
                student.getBiometry(),
                student.getInschool(),
                student.getSchoolUnit().getId(),
                student.getStatus().name()
        );
    }

    public Student updateEntityFromDTO(StudentRequestDTO dto, Student student) {
        if (StringUtils.hasText(dto.getUsername())) {
            student.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            student.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            student.setSchoolUnit(
                    schoolUnitService.findById(dto.getUnitId())
            );
        }
        if (StringUtils.hasText(dto.getName())) {
            student.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getRa())) {
            student.setRa(dto.getRa());
        }
        if (dto.getRm() != null && dto.getRm() > 0) {
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
                    schoolClassService.findById(dto.getSchoolClassId())
            );
        }
        if (dto.getBirthdate() != null) {
            if (LocalDate.parse(dto.getBirthdate()).isBefore(LocalDate.now())) {
                student.setBirthdate(LocalDate.parse(dto.getBirthdate()));
            }
        }
        if (StringUtils.hasText(dto.getPhoto())) {
            student.setPhotoUrl(dto.getPhoto());
        }
        if (dto.getSendNotification() != null) {
            student.setSendNotification(dto.getSendNotification());
        }
        return student;
    }
}
