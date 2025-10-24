package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.TeacherMapper;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @PostMapping
    public TeacherResponseDTO create(@RequestBody @Valid TeacherRequestDTO dto) {
        return teacherMapper.toDTO(
                teacherService.create(dto)
        );
    }

    @GetMapping
    public List<TeacherResponseDTO> findAll() {
        return teacherService.findAll().stream().map(teacherMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public TeacherResponseDTO findById(@PathVariable("id") Long id) {
        return teacherMapper.toDTO(teacherService.findById(id));
    }

    @GetMapping("/username/{username}")
    public TeacherResponseDTO findByUsername(@PathVariable("username") String username) {
        return teacherMapper.toDTO(teacherService.findByUsername(username));
    }

    @PutMapping("/{id}")
    public TeacherResponseDTO update(
            @RequestBody @Valid TeacherRequestDTO dto,
            @PathVariable("id") Long id) {
        return teacherMapper.toDTO(
                teacherService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        teacherService.delete(teacherService.findById(id));
    }
}
