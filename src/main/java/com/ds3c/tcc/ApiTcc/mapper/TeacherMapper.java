package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TeacherMapper {
    private final UserService userService;
    private final TeacherService teacherService;

    @Autowired
    @Lazy
    public TeacherMapper(
            UserService userService,
            TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    public Teacher toEntity(TeacherRequestDTO dto, Long userId) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setCpf(dto.getCpf());
        teacher.setEmail(dto.getEmail());
        teacher.setPhone(dto.getPhone());
        teacher.setSubjectIds(dto.getSubjectIds());
        teacher.setSchoolClassIds(dto.getSchoolClassIds());
        teacher.setUserId(userId);
        return teacher;
    }

    public TeacherResponseDTO toDTO(Teacher teacher) {
        User user = userService.findById(teacher.getUserId());

        return new TeacherResponseDTO(
                teacher.getId(),
                user.getUsername(),
                user.getPassword(),
                teacher.getName(),
                teacher.getCpf(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getSubjectIds(),
                teacher.getSchoolClassIds(),
                user.getSchoolUnit().getId()
        );
    }

    public Teacher updateEntityFromDTO(TeacherRequestDTO dto, Long id) {
        Teacher teacher = teacherService.findById(id);
        if (StringUtils.hasText(dto.getUsername())
                || StringUtils.hasText(dto.getPassword())) {
            userService.update(dto, teacher.getUserId());
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