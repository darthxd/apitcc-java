package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TeacherMapper {
    private final TeacherService teacherService;
    private final SchoolUnitService schoolUnitService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public TeacherMapper(
            TeacherService teacherService,
            SchoolUnitService schoolUnitService, PasswordEncoder passwordEncoder) {
        this.teacherService = teacherService;
        this.schoolUnitService = schoolUnitService;
        this.passwordEncoder = passwordEncoder;
    }

    public Teacher toEntity(TeacherRequestDTO dto) {
        Teacher teacher = new Teacher();
        SchoolUnit unit = schoolUnitService
                .findById(dto.getUnitId());

        // user
        teacher.setUsername(dto.getUsername());
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        teacher.setRole(RolesEnum.ROLE_TEACHER);
        teacher.setSchoolUnit(unit);

        // teacher
        teacher.setName(dto.getName());
        teacher.setCpf(dto.getCpf());
        teacher.setEmail(dto.getEmail());
        teacher.setPhone(dto.getPhone());
        teacher.setSubjectIds(dto.getSubjectIds());
        teacher.setSchoolClassIds(dto.getSchoolClassIds());

        return teacher;
    }

    public TeacherResponseDTO toDTO(Teacher teacher) {
        return new TeacherResponseDTO(
                teacher.getId(),
                teacher.getUsername(),
                teacher.getPassword(),
                teacher.getName(),
                teacher.getCpf(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getSubjectIds(),
                teacher.getSchoolClassIds(),
                teacher.getSchoolUnit().getId()
        );
    }

    public Teacher updateEntityFromDTO(TeacherRequestDTO dto, Long id) {
        Teacher teacher = teacherService.findById(id);
        if (StringUtils.hasText(dto.getUsername())){
            teacher.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            teacher.setSchoolUnit(
                    schoolUnitService.findById(dto.getUnitId())
            );
        }
        if (StringUtils.hasText(dto.getName())) {
            teacher.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getCpf())) {
            teacher.setCpf(dto.getCpf());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            teacher.setEmail(dto.getEmail());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            teacher.setPhone(dto.getPhone());
        }
        if (dto.getSubjectIds() != null) {
            teacher.setSubjectIds(dto.getSubjectIds());
        }
        if (dto.getSchoolClassIds() != null) {
            teacher.setSchoolClassIds(dto.getSchoolClassIds());
        }
        return teacher;
    }
}