package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.GradeMapper;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GradeService extends CRUDService<Grade, Long> {
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final SchoolClassService schoolClassService;

    public GradeService(
            GradeRepository gradeRepository,
            GradeMapper gradeMapper, SchoolClassService schoolClassService) {
        super(Grade.class, gradeRepository);
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.schoolClassService = schoolClassService;
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
        Grade grade = findById(id);
        return save(gradeMapper.updateEntityFromDTO(dto, grade));
    }

    public List<Grade> findByStudent(Long studentId) {
        return gradeRepository.findAllByStudentId(studentId);
    }

    public List<Grade> findByStudentAndSubject(Long studentId, Long subjectId) {
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
    }

    public Map<String, Double> getAveragePerformanceByClassAndBimester(Long classId, Integer bimester) {
        SchoolClass schoolClass = schoolClassService.findById(classId);
        List<Grade> grades = gradeRepository.findBySchoolClassAndBimester(classId, bimester);
        return grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getSubject().getName(),
                        Collectors.averagingDouble(Grade::getGrade)
                ));
    }

    public Map<String, Double> getAveragePerformanceByClass(Long classId) {
        SchoolClass schoolClass = schoolClassService.findById(classId);

        List<Grade> grades = gradeRepository.findBySchoolClass(classId);
        return grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getSubject().getName(),
                        Collectors.averagingDouble(Grade::getGrade)
                ));
    }
}
