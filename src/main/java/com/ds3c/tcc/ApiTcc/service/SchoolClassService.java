package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.exception.SchoolClassNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.SchoolClassMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;
    private final StudentService studentService;

    @Autowired
    @Lazy
    public SchoolClassService(SchoolClassRepository schoolClassRepository,
                              SchoolClassMapper schoolClassMapper,
                              StudentService studentService) {
        this.schoolClassRepository = schoolClassRepository;
        this.schoolClassMapper = schoolClassMapper;
        this.studentService = studentService;
    }

    public String generateSchoolClassName(SchoolClass schoolClass) {
        final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftName = "";
        String gradeName = "";
        String classIdentifier = "";

        List<SchoolClass> schoolClasses = schoolClassRepository
                .findByCourseAndGradeAndShift(
                schoolClass.getCourse(),
                schoolClass.getGrade(),
                schoolClass.getShift()
        );

        classIdentifier = String.valueOf(letters.charAt(schoolClasses.size()));

        switch (schoolClass.getShift()) {
            case ShiftsEnum.MORNING -> shiftName = "M";
            case ShiftsEnum.AFTERNOON -> shiftName = "T";
            case ShiftsEnum.NIGHT -> shiftName = "N";
        }

        switch (schoolClass.getGrade()) {
            case GradesEnum.FIRST_YEAR -> gradeName = "1";
            case GradesEnum.SECOND_YEAR -> gradeName = "2";
            case GradesEnum.THIRD_YEAR -> gradeName = "3";
        }

        return schoolClass.getCourse().name()
                + shiftName + gradeName + classIdentifier;
    }

    public SchoolClass createSchoolClass(SchoolClassRequestDTO dto) {
        return schoolClassRepository.save(
                schoolClassMapper.toModel(dto)
        );
    }

    public SchoolClass getSchoolClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
    }

    public List<SchoolClass> listSchoolClass() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass updateSchoolClass(SchoolClassRequestDTO dto,
                                         Long id) {
        return schoolClassRepository.save(
                schoolClassMapper.updateModelFromDTO(dto, id));
    }

    public void deleteSchoolClass(Long id) {
        SchoolClass schoolClass = getSchoolClassById(id);
        schoolClassRepository.delete(schoolClass);
    }

    public List<SchoolClass> listSchoolClassById(Set<Long> idSet) {
        return schoolClassRepository.findAllById(idSet);
    }
}
