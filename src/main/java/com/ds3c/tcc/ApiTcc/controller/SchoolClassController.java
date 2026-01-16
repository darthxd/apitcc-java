package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolClassMapper;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schoolclass")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassMapper schoolClassMapper;
    private final SchoolClassService schoolClassService;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public List<SchoolClassResponseDTO> findAll() {
        return schoolClassService.findAll().stream().map(schoolClassMapper::toDTO).toList();
    }

    @PostMapping
    public SchoolClassResponseDTO create(
            @RequestBody @Valid SchoolClassRequestDTO dto) {
        return schoolClassMapper.toDTO(
                schoolClassService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public SchoolClassResponseDTO findById(@PathVariable Long id) {
        return schoolClassMapper.toDTO(
                schoolClassService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public SchoolClassResponseDTO update(
            @RequestBody @Valid SchoolClassRequestDTO dto,
            @PathVariable("id") Long id) {
        return schoolClassMapper.toDTO(
                schoolClassService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        schoolClassService.delete(schoolClassService.findById(id));
    }

    @GetMapping("/{id}/students")
    public List<StudentResponseDTO> findAllBySchoolClass(@PathVariable("id") Long id) {
        return studentService.findAllBySchoolClass(id).stream().map(studentMapper::toDTO).toList();
    }
}
