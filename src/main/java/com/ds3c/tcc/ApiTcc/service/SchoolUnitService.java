package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.repository.SchoolUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolUnitService extends CRUDService<SchoolUnit, Long>{
    public SchoolUnitService(SchoolUnitRepository schoolUnitRepository) {
        super(schoolUnitRepository);
    }
}
