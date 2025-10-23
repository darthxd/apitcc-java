package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.exception.TeacherNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.TeacherMapper;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends CRUDService<Teacher, Long>{
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserService userService;

    @Autowired
    @Lazy
    public TeacherService(
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper,
            UserService userService) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
    }

    public Teacher findByUsername(String username) {
        return teacherRepository.findByUserId(
                userService.findByUsername(username).getId())
                .orElseThrow(() -> new TeacherNotFoundException(username));
    }

    public Teacher create(TeacherRequestDTO dto) {
        User user = userService.create(dto, RolesEnum.ROLE_TEACHER, dto.getUnitId());
        Teacher teacher = teacherMapper.toEntity(dto, user.getId());
        return save(teacher);
    }

    public Teacher update(TeacherRequestDTO dto, Long id) {
        return save(teacherMapper.updateEntityFromDTO(dto, id));
    }
}
