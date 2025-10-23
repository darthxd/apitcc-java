package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitResponseDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SchoolUnitMapper {

    private final SchoolUnitService schoolUnitService;

    @Lazy
    public SchoolUnitMapper(SchoolUnitService schoolUnitService) {
        this.schoolUnitService = schoolUnitService;
    }

    public SchoolUnitResponseDTO toDTO(SchoolUnit schoolUnit) {
        return new SchoolUnitResponseDTO(
                schoolUnit.getId(),
                schoolUnit.getName(),
                schoolUnit.getAddress(),
                schoolUnit.getPhone(),
                schoolUnit.getEmail()
        );
    }

    public SchoolUnit toEntity(SchoolUnitRequestDTO dto) {
        SchoolUnit schoolUnit = new SchoolUnit();

        schoolUnit.setName(dto.getName());
        schoolUnit.setAddress(dto.getAddress());
        schoolUnit.setPhone(dto.getPhone());
        schoolUnit.setEmail(dto.getEmail());

        return schoolUnit;
    }

    public SchoolUnit updateEntityFromDTO(SchoolUnitRequestDTO dto, Long id) {
        SchoolUnit schoolUnit = schoolUnitService.findById(id);
        if (StringUtils.hasText(dto.getName())) {
            schoolUnit.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getAddress())) {
            schoolUnit.setAddress(dto.getAddress());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            schoolUnit.setPhone(dto.getPhone());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            schoolUnit.setEmail(dto.getEmail());
        }
        return schoolUnit;
    }
}
