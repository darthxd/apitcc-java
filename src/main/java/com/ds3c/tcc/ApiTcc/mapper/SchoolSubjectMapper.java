package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectResponseDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class SchoolSubjectMapper {
    public SchoolSubject toEntity(SchoolSubjectRequestDTO schoolSubjectRequestDTO) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(schoolSubjectRequestDTO.getName());
        schoolSubject.setWorkload(schoolSubjectRequestDTO.getWorkload());
        return schoolSubject;
    }

    public SchoolSubjectResponseDTO toDTO(SchoolSubject schoolSubject) {
        return new SchoolSubjectResponseDTO(
                schoolSubject.getId(),
                schoolSubject.getName(),
                schoolSubject.getWorkload()
        );
    }

    public SchoolSubject updateEntityFromDTO(SchoolSubjectRequestDTO schoolSubjectRequestDTO, SchoolSubject schoolSubject) {
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
