package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.TeacherMapper;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherController(
            TeacherService teacherService,
            TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @PostMapping
    public TeacherResponseDTO createTeacher(@RequestBody @Valid TeacherRequestDTO dto) {
        return teacherMapper.toDTO(
                teacherService.createTeacher(dto)
        );
    }

    @GetMapping
    public List<TeacherResponseDTO> listTeachers() {
        return teacherService.listTeacher().stream().map(teacherMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public TeacherResponseDTO getTeacherById(@PathVariable("id") Long id) {
        return teacherMapper.toDTO(teacherService.getTeacherById(id));
    }

    @GetMapping("/username/{username}")
    public TeacherResponseDTO getTeacherByUsername(@PathVariable("username") String username) {
        return teacherMapper.toDTO(teacherService.getTeacherByUsername(username));
    }

    @PutMapping("/{id}")
    public TeacherResponseDTO updateTeacher(@RequestBody @Valid TeacherRequestDTO dto,
                                            @PathVariable("id") Long id) {
        return teacherMapper.toDTO(
                teacherService.updateTeacher(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}
