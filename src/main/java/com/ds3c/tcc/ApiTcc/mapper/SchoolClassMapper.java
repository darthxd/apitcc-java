package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class SchoolClassMapper {
    private final SchoolClassService schoolClassService;

    public SchoolClass toEntity(SchoolClassRequestDTO dto) {
        SchoolClass schoolClass = new SchoolClass();
        try {
            schoolClass.setGrade(GradesEnum.valueOf(dto.getGrade()));
            schoolClass.setCourse(CoursesEnum.valueOf(dto.getCourse()));
            schoolClass.setShift(ShiftsEnum.valueOf(dto.getShift()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "One or more of the Enum values passed are incorrect (Grade, Course and/or Shift).");
        }
        schoolClass.setStudentsLimit(dto.getStudentsLimit());
        return schoolClass;
    }

    public SchoolClassResponseDTO toDTO(SchoolClass schoolClass) {
        return new SchoolClassResponseDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getGrade().name(),
                schoolClass.getCourse().name(),
                schoolClass.getShift().name(),
                schoolClass.getStudentsLimit().toString(),
                schoolClass.getStudentsCount().toString()
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

    public SchoolClass updateEntityFromDTO(SchoolClassRequestDTO dto, Long id) {
        SchoolClass schoolClass = schoolClassService.findById(id);
        if (StringUtils.hasText(dto.getGrade())) {
            try {
                schoolClass.setGrade(GradesEnum.valueOf(dto.getGrade()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The grade with name "+dto.getGrade()+" does not exist.");
            }
        }
        if (StringUtils.hasText(dto.getCourse())) {
            try {
                schoolClass.setCourse(CoursesEnum.valueOf(dto.getCourse()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The course with name "+dto.getGrade()+" does not exist.");
            }
        }
        if (StringUtils.hasText(dto.getShift())) {
            try {
                schoolClass.setShift(ShiftsEnum.valueOf(dto.getShift()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The shift "+dto.getGrade()+" does not exist.");
            }
        }
        if (dto.getStudentsLimit() != null) {
            schoolClass.setStudentsLimit(dto.getStudentsLimit());
        }
        return schoolClass;
    }
}
