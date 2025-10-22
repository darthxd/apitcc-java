package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.exception.GradeNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.GradeMapper;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    public GradeService(GradeRepository gradeRepository, GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(GradeNotFoundException::new);
    }

    public Grade createGrade(GradeRequestDTO dto) {
        return gradeRepository.save(gradeMapper.toEntity(dto));
    }

    public Grade updateGrade(GradeRequestDTO dto, Long id) {
        return gradeRepository.save(gradeMapper.updateEntityFromDTO(dto, id));
    }

    public void deleteGrade(Long id) {
        Grade grade = getGradeById(id);
        gradeRepository.delete(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findAllByStudentId(studentId);
    }

    public List<Grade> getGradesByStudentAndSubject(Long studentId, Long subjectId) {
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
    }
}
