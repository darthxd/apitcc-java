package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Grade.GradeResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.GradeMapper;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    private final GradeMapper gradeMapper;

    @PostMapping
    public GradeResponseDTO create(@RequestBody @Valid GradeRequestDTO dto) {
        Grade grade = gradeService.create(dto);
        return gradeMapper.toDTO(grade);
    }

    @GetMapping("/student/{id}")
    public List<GradeResponseDTO> findByStudent(@PathVariable("id") Long studentId) {
        return gradeService.findByStudent(studentId)
                .stream().map(gradeMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/student/{id}/subject/{subjectId}")
    public List<GradeResponseDTO> findByStudentAndSubject(
            @PathVariable("id") Long studentId,
            @PathVariable("subjectId") Long subjectId) {
        return gradeService.findByStudentAndSubject(studentId, subjectId)
                .stream().map(gradeMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/class/{classId}/performance/{bimester}")
    public Map<String, Double> getAveragePerformanceByClassAndBimester(
            @PathVariable("classId") Long classId,
            @PathVariable("bimester") Integer bimester) {
        return gradeService.getAveragePerformanceByClassAndBimester(classId, bimester);
    }

    @GetMapping("/class/{classId}/performance")
    public Map<String, Double> getAveragePerformanceByClass(
            @PathVariable("classId") Long classId) {
        return gradeService.getAveragePerformanceByClass(classId);
    }
}
