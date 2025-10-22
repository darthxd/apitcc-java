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

    public Grade getById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(GradeNotFoundException::new);
    }

    public Grade create(GradeRequestDTO dto) {
        return gradeRepository.save(gradeMapper.toEntity(dto));
    }

    public Grade update(GradeRequestDTO dto, Long id) {
        return gradeRepository.save(gradeMapper.updateEntityFromDTO(dto, id));
    }

    public void delete(Long id) {
        Grade grade = getById(id);
        gradeRepository.delete(grade);
    }

    public List<Grade> getByStudent(Long studentId) {
        return gradeRepository.findAllByStudentId(studentId);
    }

    public List<Grade> getByStudentAndSubject(Long studentId, Long subjectId) {
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
    }
}
