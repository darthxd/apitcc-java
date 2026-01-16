package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.YearsEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.mapper.SchoolClassMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService extends CRUDService<SchoolClass, Long>{
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;

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
        String yearName = "";
        String classIdentifier;

        List<SchoolClass> schoolClasses = schoolClassRepository
                .findByCourseAndYearAndShift(
                schoolClass.getCourse(),
                schoolClass.getYear(),
                schoolClass.getShift()
        );

        classIdentifier = String.valueOf(letters.charAt(schoolClasses.size()));

        switch (schoolClass.getShift()) {
            case ShiftsEnum.MORNING -> shiftName = "M";
            case ShiftsEnum.AFTERNOON -> shiftName = "T";
            case ShiftsEnum.NIGHT -> shiftName = "N";
        }

        switch (schoolClass.getYear()) {
            case YearsEnum.FIRST_YEAR -> yearName = "1";
            case YearsEnum.SECOND_YEAR -> yearName = "2";
            case YearsEnum.THIRD_YEAR -> yearName = "3";
        }

        return schoolClass.getCourse().name()
                + shiftName + yearName + classIdentifier;
    }

    public SchoolClass create(SchoolClassRequestDTO dto) {
        SchoolClass schoolClass = schoolClassMapper.toEntity(dto);
        schoolClass.setName(generateName(schoolClass));
        return save(schoolClass);
    }

    public SchoolClass update(SchoolClassRequestDTO dto, Long id) {
        SchoolClass schoolClass = findById(id);
        schoolClass = schoolClassMapper.updateEntityFromDTO(dto, schoolClass);
        schoolClass.setName(generateName(schoolClass));
        return save(schoolClass);
    }

    public SchoolClass findAvailable(String year, String course, String shift) {
        try {
            System.out.println("year: "+year);
            System.out.println("course: "+course);
            System.out.println("shift: "+shift);
            return schoolClassRepository.findAvailableClass(
                    YearsEnum.valueOf(year),
                    CoursesEnum.valueOf(course),
                    ShiftsEnum.valueOf(shift)
            ).orElseThrow(() -> new EntityNotFoundException("No available class was found."));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The grade, course and shift must be valid.");
        }
    }
}
