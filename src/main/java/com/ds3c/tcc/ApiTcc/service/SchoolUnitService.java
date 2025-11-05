package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.mapper.SchoolUnitMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Secretary;
import com.ds3c.tcc.ApiTcc.repository.SchoolUnitRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SchoolUnitService extends CRUDService<SchoolUnit, Long>{
    private final SchoolUnitMapper schoolUnitMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecretaryService secretaryService;

    public SchoolUnitService(
            SchoolUnitRepository schoolUnitRepository,
            SchoolUnitMapper schoolUnitMapper,
            PasswordEncoder passwordEncoder, SecretaryService secretaryService) {
        super(SchoolUnit.class, schoolUnitRepository);
        this.schoolUnitMapper = schoolUnitMapper;
        this.passwordEncoder = passwordEncoder;
        this.secretaryService = secretaryService;
    }

    private String generateSecretaryUsername(SchoolUnit unit) {
        return unit.getName().toLowerCase().replaceAll("\\s+", ".");
    }

    private String generateSecretaryPassword(SchoolUnit unit) {
        return unit.getName().toLowerCase().replaceAll("\\s+", "");
    }

    public SchoolUnit create(SchoolUnitRequestDTO dto) {
        SchoolUnit unit = save(schoolUnitMapper.toEntity(dto));
        Secretary secretary = new Secretary();

        secretary.setUsername(generateSecretaryUsername(unit));
        secretary.setPassword(passwordEncoder.encode(generateSecretaryPassword(unit)));
        secretary.setRole(RolesEnum.ROLE_SECRETARY);

        secretary.setEmail(unit.getEmail());
        secretary.setPhone(unit.getPhone());

        secretary.setSchoolUnit(unit);

        secretaryService.save(secretary);

        return unit;
    }

    public SchoolUnit update(SchoolUnitRequestDTO dto, Long id) {
        return save(schoolUnitMapper.updateEntityFromDTO(dto, id));
    }
}
