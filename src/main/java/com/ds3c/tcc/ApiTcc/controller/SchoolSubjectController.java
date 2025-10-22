package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolSubjectMapper;
import com.ds3c.tcc.ApiTcc.service.SchoolSubjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schoolsubject")
public class SchoolSubjectController {
    private final SchoolSubjectMapper schoolSubjectMapper;
    private final SchoolSubjectService schoolSubjectService;

    public SchoolSubjectController(
            SchoolSubjectMapper schoolSubjectMapper,
            SchoolSubjectService schoolSubjectService) {
        this.schoolSubjectMapper = schoolSubjectMapper;
        this.schoolSubjectService = schoolSubjectService;
    }

    @GetMapping
    public List<SchoolSubjectResponseDTO> list() {
        return schoolSubjectService.list().stream().map(schoolSubjectMapper::toDTO).toList();
    }

    @PostMapping
    public SchoolSubjectResponseDTO create(
            @RequestBody @Valid SchoolSubjectRequestDTO dto) {
        return schoolSubjectMapper.toDTO(
                schoolSubjectService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public SchoolSubjectResponseDTO getById(
            @PathVariable("id") Long id) {
        return schoolSubjectMapper.toDTO(
                schoolSubjectService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public SchoolSubjectResponseDTO update(
            @RequestBody @Valid SchoolSubjectRequestDTO dto,
            @PathVariable("id") Long id) {
        return schoolSubjectMapper.toDTO(
                schoolSubjectService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        schoolSubjectService.delete(id);
    }
}
