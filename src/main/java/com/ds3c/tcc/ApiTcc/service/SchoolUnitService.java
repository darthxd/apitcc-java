package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolUnitMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.repository.SchoolUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolUnitService extends CRUDService<SchoolUnit, Long>{
    private final SchoolUnitMapper schoolUnitMapper;

    public SchoolUnitService(SchoolUnitRepository schoolUnitRepository, SchoolUnitMapper schoolUnitMapper) {
        super(SchoolUnit.class, schoolUnitRepository);
        this.schoolUnitMapper = schoolUnitMapper;
    }

    public SchoolUnit create(SchoolUnitRequestDTO dto) {
        return save(schoolUnitMapper.toEntity(dto));
    }

    public SchoolUnit update(SchoolUnitRequestDTO dto, Long id) {
        return save(schoolUnitMapper.updateEntityFromDTO(dto, id));
    }
}
