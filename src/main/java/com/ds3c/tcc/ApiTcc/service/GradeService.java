package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.GradeMapper;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService extends CRUDService<Grade, Long> {
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    public GradeService(
            GradeRepository gradeRepository,
            GradeMapper gradeMapper) {
        super(gradeRepository);
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
    }

    public Grade create(GradeRequestDTO dto) {
        if (gradeRepository.existsByStudentIdAndSubjectIdAndBimesterAndYear(
                dto.getStudentId(), dto.getSubjectId(),dto.getBimester(), dto.getYear())) {
            throw new IllegalArgumentException(
                    "There is already a grade for this student and subject in this bimester and year.");
        }
        return save(gradeMapper.toEntity(dto));
    }

    public Grade update(GradeRequestDTO dto, Long id) {
        return save(gradeMapper.updateEntityFromDTO(dto, id));
    }

    public List<Grade> findByStudent(Long studentId) {
        return gradeRepository.findAllByStudentId(studentId);
    }

    public List<Grade> findByStudentAndSubject(Long studentId, Long subjectId) {
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
    }
}
