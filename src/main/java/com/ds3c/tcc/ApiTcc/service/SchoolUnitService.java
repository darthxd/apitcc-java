package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolUnitMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.repository.SchoolUnitRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class SchoolUnitService extends CRUDService<SchoolUnit, Long>{
    private final SchoolUnitMapper schoolUnitMapper;

    public SchoolUnitService(
            SchoolUnitRepository schoolUnitRepository,
            SchoolUnitMapper schoolUnitMapper) {
        super(SchoolUnit.class, schoolUnitRepository);
        this.schoolUnitMapper = schoolUnitMapper;
    }

    public SchoolUnit create(SchoolUnitRequestDTO dto, Consumer<SchoolUnit> onCreated) {
        SchoolUnit unit = save(schoolUnitMapper.toEntity(dto));

        onCreated.accept(unit);

        return unit;
    }

    public SchoolUnit update(SchoolUnitRequestDTO dto, Long id) {
        SchoolUnit unit = findById(id);
        return save(schoolUnitMapper.updateEntityFromDTO(dto, unit));
    }
}
