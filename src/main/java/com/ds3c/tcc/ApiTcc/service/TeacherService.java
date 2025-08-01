package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResumeDTO;
import com.ds3c.tcc.ApiTcc.exception.TeacherNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.TeacherMapper;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public List<TeacherResumeDTO> listTeacherResumeDTO() {
        return teacherMapper.toListResumeDTO(
                teacherRepository.findAll()
        );
    }
}
