package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.exception.SchoolUnitNotFoundException;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.repository.SchoolUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolUnitService {
    private final SchoolUnitRepository schoolUnitRepository;

    public SchoolUnitService(SchoolUnitRepository schoolUnitRepository) {
        this.schoolUnitRepository = schoolUnitRepository;
    }

    public SchoolUnit getSchoolUnitById(Long id) {
        return schoolUnitRepository.findById(id)
                .orElseThrow(() -> new SchoolUnitNotFoundException(id));
    }
}
