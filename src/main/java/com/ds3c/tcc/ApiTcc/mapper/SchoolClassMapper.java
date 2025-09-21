package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolClassMapper {
    private final SchoolClassService schoolClassService;

    @Autowired
    @Lazy
    public SchoolClassMapper(
            SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    public SchoolClass toModel(SchoolClassRequestDTO schoolClassRequestDTO) {
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
                schoolClassService.generateSchoolClassName(schoolClass)
        );
        return schoolClass;
    }

    public SchoolClassResponseDTO toDTO(SchoolClass schoolClass) {
        SchoolClassResponseDTO schoolClassResponseDTO = new SchoolClassResponseDTO();
        schoolClassResponseDTO.setId(schoolClass.getId());
        schoolClassResponseDTO.setName(schoolClass.getName());
        schoolClassResponseDTO.setGrade(schoolClass.getGrade().name());
        schoolClassResponseDTO.setCourse(schoolClass.getCourse().name());
        schoolClassResponseDTO.setShift(schoolClass.getShift().name());
        schoolClassResponseDTO.setTeacherIds(schoolClass.getTeacherIds());
        return schoolClassResponseDTO;
    }

    public SchoolClassResumeDTO toResumeDTO(SchoolClass schoolClass) {
        SchoolClassResumeDTO dto = new SchoolClassResumeDTO();
        dto.setId(schoolClass.getId());
        dto.setName(schoolClass.getName());
        dto.setGrade(schoolClass.getGrade().name());
        dto.setCourse(schoolClass.getCourse().name());
        dto.setShift(schoolClass.getShift().name());
        return dto;
    }

    public List<SchoolClassResponseDTO> toListDTO(List<SchoolClass> schoolClassList) {
        List<SchoolClassResponseDTO> schoolClassResponseDTOList = new ArrayList<>();
        for (SchoolClass schoolClass : schoolClassList) {
            schoolClassResponseDTOList.add(toDTO(schoolClass));
        }
        return schoolClassResponseDTOList;
    }

    public SchoolClass updateModelFromDTO(SchoolClassRequestDTO schoolClassRequestDTO, Long id) {
        SchoolClass schoolClass = schoolClassService.getSchoolClassById(id);
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
                schoolClassService.generateSchoolClassName(schoolClass)
        );
        return schoolClass;
    }
}
