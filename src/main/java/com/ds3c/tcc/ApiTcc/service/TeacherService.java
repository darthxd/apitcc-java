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

import java.util.List;
import java.util.Set;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserService userService;

    @Autowired
    @Lazy
    public TeacherService(
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper,
            UserService userService) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
    }

    public Teacher create(TeacherRequestDTO dto) {
        User user = userService.create(dto, RolesEnum.ROLE_TEACHER, dto.getUnitId());
        Teacher teacher = teacherMapper.toEntity(dto, user.getId());
        return teacherRepository.save(teacher);
    }

    public Teacher getById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public Teacher getByUsername(String username) {
        return teacherRepository.findByUserId(
                userService.getByUsername(username).getId())
                .orElseThrow(() -> new TeacherNotFoundException(username));
    }

    public List<Teacher> list() {
        return teacherRepository.findAll();
    }

    public Teacher update(
            TeacherRequestDTO dto,
            Long id) {
        return teacherRepository.save(
                teacherMapper.updateModelFromDTO(dto, id)
        );
    }

    public void delete(Long id) {
        Teacher teacher = getById(id);
        userService.delete(teacher.getUserId());
        teacherRepository.delete(teacher);

    }

    public List<Teacher> listById(Set<Long> idSet) {
        return teacherRepository.findAllById(idSet);
    }
}
