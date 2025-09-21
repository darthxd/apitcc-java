package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectResponseDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.service.SchoolSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolSubjectMapper {
    private final SchoolSubjectService schoolSubjectService;

    @Autowired
    @Lazy
    public SchoolSubjectMapper(
            SchoolSubjectService schoolSubjectService) {
        this.schoolSubjectService = schoolSubjectService;
    }

    public SchoolSubject toModel(SchoolSubjectRequestDTO schoolSubjectRequestDTO) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(schoolSubjectRequestDTO.getName());
        schoolSubject.setWorkload(schoolSubjectRequestDTO.getWorkload());
        return schoolSubject;
    }

    public SchoolSubjectResponseDTO toDTO(SchoolSubject schoolSubject) {
        SchoolSubjectResponseDTO schoolSubjectResponseDTO = new SchoolSubjectResponseDTO();
        schoolSubjectResponseDTO.setId(schoolSubject.getId());
        schoolSubjectResponseDTO.setName(schoolSubject.getName());
        schoolSubjectResponseDTO.setWorkload(schoolSubject.getWorkload());
        return schoolSubjectResponseDTO;
    }

    public List<SchoolSubjectResponseDTO> toListDTO(List<SchoolSubject> schoolSubjectList) {
        List<SchoolSubjectResponseDTO> schoolSubjectResponseDTOList = new ArrayList<>();
        for (SchoolSubject schoolSubject : schoolSubjectList) {
            schoolSubjectResponseDTOList.add(toDTO(schoolSubject));
        }
        return schoolSubjectResponseDTOList;
    }

    public SchoolSubject updateModelFromDTO(SchoolSubjectRequestDTO schoolSubjectRequestDTO, Long id) {
        SchoolSubject schoolSubject = schoolSubjectService.getSchoolSubjectById(id);
        if (StringUtils.hasText(schoolSubjectRequestDTO.getName())) {
            schoolSubject.setName(schoolSubjectRequestDTO.getName());
        }
        if (schoolSubjectRequestDTO.getWorkload() != null) {
            schoolSubject.setWorkload(
                    schoolSubjectRequestDTO.getWorkload()
            );
        }
        return schoolSubject;
    }
}
