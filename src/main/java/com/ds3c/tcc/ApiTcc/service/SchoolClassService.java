package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.mapper.SchoolClassMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.repository.SchoolClassRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService extends CRUDService<SchoolClass, Long>{
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;

    @Lazy
    public SchoolClassService(
            SchoolClassRepository schoolClassRepository,
            SchoolClassMapper schoolClassMapper) {
        super(SchoolClass.class, schoolClassRepository);
        this.schoolClassRepository = schoolClassRepository;
        this.schoolClassMapper = schoolClassMapper;
    }

    private String generateName(SchoolClass schoolClass) {
        final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftName = "";
        String gradeName = "";
        String classIdentifier;

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

    public SchoolClass create(SchoolClassRequestDTO dto) {
        SchoolClass schoolClass = schoolClassMapper.toEntity(dto);
        schoolClass.setName(generateName(schoolClass));
        return save(schoolClass);
    }

    public SchoolClass update(SchoolClassRequestDTO dto, Long id) {
        SchoolClass schoolClass = schoolClassMapper.updateEntityFromDTO(dto, id);
        schoolClass.setName(generateName(schoolClass));
        return save(schoolClass);
    }
}
