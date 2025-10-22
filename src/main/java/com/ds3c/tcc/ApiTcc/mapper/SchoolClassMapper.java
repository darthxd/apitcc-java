package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SchoolClassMapper {
    private final SchoolClassService schoolClassService;

    @Autowired
    @Lazy
    public SchoolClassMapper(
            SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    public SchoolClass toEntity(SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setTeacherIds(schoolClassRequestDTO.getTeacherIds());
        try {
            schoolClass.setGrade(GradesEnum.valueOf(schoolClassRequestDTO.getGrade()));
            schoolClass.setCourse(CoursesEnum.valueOf(schoolClassRequestDTO.getCourse()));
            schoolClass.setShift(ShiftsEnum.valueOf(schoolClassRequestDTO.getShift()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "One or more of the Enum values passed are incorrect (Grade, Course and/or Shift).");
        }
        schoolClass.setName(
                schoolClassService.generateName(schoolClass)
        );
        return schoolClass;
    }

    public SchoolClassResponseDTO toDTO(SchoolClass schoolClass) {
        return new SchoolClassResponseDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getGrade().name(),
                schoolClass.getCourse().name(),
                schoolClass.getShift().name(),
                schoolClass.getTeacherIds()
        );
    }

    public SchoolClassResumeDTO toResumeDTO(SchoolClass schoolClass) {
        return new SchoolClassResumeDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getGrade().name(),
                schoolClass.getCourse().name(),
                schoolClass.getShift().name()
        );
    }

    public SchoolClass updateEntityFromDTO(SchoolClassRequestDTO schoolClassRequestDTO, Long id) {
        SchoolClass schoolClass = schoolClassService.getById(id);
        if (StringUtils.hasText(schoolClassRequestDTO.getGrade())) {
            try {
                schoolClass.setGrade(GradesEnum.valueOf(schoolClassRequestDTO.getGrade()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The grade with name "+schoolClassRequestDTO.getGrade()+" does not exist.");
            }
        }
        if (StringUtils.hasText(schoolClassRequestDTO.getCourse())) {
            try {
                schoolClass.setCourse(CoursesEnum.valueOf(schoolClassRequestDTO.getCourse()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The course with name "+schoolClassRequestDTO.getGrade()+" does not exist.");
            }
        }
        if (StringUtils.hasText(schoolClassRequestDTO.getShift())) {
            try {
                schoolClass.setShift(ShiftsEnum.valueOf(schoolClassRequestDTO.getShift()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The shift "+schoolClassRequestDTO.getGrade()+" does not exist.");
            }
        }
        if (schoolClassRequestDTO.getTeacherIds() != null) {
            schoolClass.setTeacherIds(
                    schoolClassRequestDTO.getTeacherIds()
            );
        }
        schoolClass.setName(
                schoolClassService.generateName(schoolClass)
        );
        return schoolClass;
    }
}
