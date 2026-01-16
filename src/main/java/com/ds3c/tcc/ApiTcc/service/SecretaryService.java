package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.mapper.SecretaryMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Secretary;
import com.ds3c.tcc.ApiTcc.repository.SecretaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecretaryService extends CRUDService<Secretary, Long> {
    private final SecretaryMapper secretaryMapper;
    private final SecretaryRepository secretaryRepository;
    private final SchoolUnitService schoolUnitService;
    private final PasswordEncoder passwordEncoder;

    public SecretaryService(
            SecretaryRepository secretaryRepository,
            SecretaryMapper secretaryMapper, SchoolUnitService schoolUnitService, PasswordEncoder passwordEncoder) {
        super(Secretary.class, secretaryRepository);
        this.secretaryMapper = secretaryMapper;
        this.secretaryRepository = secretaryRepository;
        this.schoolUnitService = schoolUnitService;
        this.passwordEncoder = passwordEncoder;
    }

    public Secretary findByUsername(String username) {
        return secretaryRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The secretary with username: "+username+" was not found."));
    }

    private String generateSecretaryUsername(SchoolUnit unit) {
        return unit.getName().toLowerCase().replaceAll("\\s+", ".");
    }

    private String generateSecretaryPassword(SchoolUnit unit) {
        return unit.getName().toLowerCase().replaceAll("\\s+", "");
    }

    public Secretary create(SecretaryRequestDTO dto) {
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());
        return save(secretaryMapper.toEntity(dto, unit));
    }

    public Secretary createBySchoolUnit(SchoolUnit unit) {
        Secretary secretary = new Secretary();

        secretary.setUsername(generateSecretaryUsername(unit));
        secretary.setPassword(passwordEncoder.encode(generateSecretaryPassword(unit)));
        secretary.setRole(RolesEnum.ROLE_SECRETARY);
        secretary.setSchoolUnit(unit);
        secretary.setEmail(unit.getEmail());
        secretary.setPhone(unit.getPhone());

        return save(secretary);
    }

    public Secretary update(SecretaryRequestDTO dto, Long id) {
        Secretary secretary = findById(id);
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());
        return save(secretaryMapper.updateEntityFromDTO(dto, secretary, unit));
    }
}
