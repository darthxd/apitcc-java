package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResumeDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolClassMapper {
    private final TeacherService teacherService;

    @Autowired
    public SchoolClassMapper(
            TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public SchoolClass toModel(SchoolClassCreateDTO schoolClassCreateDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setCourse(schoolClassCreateDTO.getCourse());
        schoolClass.setShift(schoolClassCreateDTO.getShift());
        schoolClass.setGrade(schoolClassCreateDTO.getGrade());
        schoolClass.setName(schoolClassCreateDTO.getName());
        schoolClass.setTeachers();
    }

    public SchoolClassResponseDTO toDTO(SchoolClass schoolClass) {
        SchoolClassResponseDTO schoolClassResponseDTO = new SchoolClassResponseDTO();
        schoolClassResponseDTO.setId(schoolClass.getId());
        schoolClassResponseDTO.setName(schoolClass.getName());
        schoolClassResponseDTO.setGrade(schoolClass.getGrade());
        schoolClassResponseDTO.setCourse(schoolClass.getCourse());
        schoolClassResponseDTO.setShift(schoolClass.getShift());
        List<TeacherResumeDTO> teacherResumeDTOList = teacherService.listTeacherResumeDTO();
        schoolClassResponseDTO.setTeachers(teacherResumeDTOList);
        return schoolClassResponseDTO;
    }

    public List<SchoolClassResponseDTO> toListDTO(List<SchoolClass> schoolClassList) {
        List<SchoolClassResponseDTO> schoolClassResponseDTOList = new ArrayList<>();
        for (SchoolClass schoolClass : schoolClassList) {
            schoolClassResponseDTOList.add(toDTO(schoolClass));
        }
        return schoolClassResponseDTOList;
    }
}
