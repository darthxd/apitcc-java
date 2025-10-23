package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.TeacherMapper;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends CRUDService<Teacher, Long>{
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    @Lazy
    public TeacherService(
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public Teacher findByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The teacher with username: "+username+" was not found."));
    }

    public Teacher create(TeacherRequestDTO dto) {
        return save(teacherMapper.toEntity(dto));
    }

    public Teacher update(TeacherRequestDTO dto, Long id) {
        return save(teacherMapper.updateEntityFromDTO(dto, id));
    }
}
