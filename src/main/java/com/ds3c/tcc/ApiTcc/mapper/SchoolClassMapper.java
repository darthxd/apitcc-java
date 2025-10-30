package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.YearsEnum;
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
            schoolClass.setYear(YearsEnum.valueOf(dto.getYear()));
            schoolClass.setCourse(CoursesEnum.valueOf(dto.getCourse()));
            schoolClass.setShift(ShiftsEnum.valueOf(dto.getShift()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "One or more of the Enum values passed are incorrect (Grade, Course and/or Shift).");
        }
        schoolClass.setStudentsLimit(dto.getStudentsLimit());
        schoolClass.setStudentsCount(0);
        return schoolClass;
    }

    public SchoolClassResponseDTO toDTO(SchoolClass schoolClass) {
        return new SchoolClassResponseDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getYear().name(),
                schoolClass.getCourse().name(),
                schoolClass.getShift().name(),
                schoolClass.getStudentsLimit(),
                schoolClass.getStudentsCount() == null ? 0 : schoolClass.getStudentsCount()
        );
    }

    public SchoolClass updateEntityFromDTO(SchoolClassRequestDTO dto, Long id) {
        SchoolClass schoolClass = schoolClassService.findById(id);
        if (StringUtils.hasText(dto.getYear())) {
            try {
                schoolClass.setYear(YearsEnum.valueOf(dto.getYear()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The grade with name "+dto.getYear()+" does not exist.");
            }
        }
        if (StringUtils.hasText(dto.getCourse())) {
            try {
                schoolClass.setCourse(CoursesEnum.valueOf(dto.getCourse()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The course with name "+dto.getYear()+" does not exist.");
            }
        }
        if (StringUtils.hasText(dto.getShift())) {
            try {
                schoolClass.setShift(ShiftsEnum.valueOf(dto.getShift()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "The shift "+dto.getYear()+" does not exist.");
            }
        }
        if (dto.getStudentsLimit() != null) {
            schoolClass.setStudentsLimit(dto.getStudentsLimit());
        }
        return schoolClass;
    }
}
