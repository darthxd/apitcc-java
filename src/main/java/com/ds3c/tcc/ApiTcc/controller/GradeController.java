package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Grade.GradeResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.GradeMapper;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grade")
public class GradeController {
    private final GradeService gradeService;
    private final GradeMapper gradeMapper;

    public GradeController(GradeService gradeService, GradeMapper gradeMapper) {
        this.gradeService = gradeService;
        this.gradeMapper = gradeMapper;
    }

    @PostMapping
    public GradeResponseDTO createGrade(@RequestBody @Valid GradeRequestDTO dto) {
        Grade grade = gradeService.createGrade(dto);
        return gradeMapper.toDTO(grade);
    }

    @GetMapping("/student/{id}")
    public List<GradeResponseDTO> getStudentGrades(@PathVariable("id") Long studentId) {
        return gradeService.getGradesByStudent(studentId).stream().map(gradeMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/student/{id}/subject/{subjectId}")
    public List<GradeResponseDTO> getStudentGradesBySubject(
            @PathVariable("id") Long studentId,
            @PathVariable("subjectId") Long subjectId) {
        return gradeService.getGradesByStudentAndSubject(studentId, subjectId)
                .stream().map(gradeMapper::toDTO).collect(Collectors.toList());
    }
}
