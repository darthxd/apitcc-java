package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.SecretaryMapper;
import com.ds3c.tcc.ApiTcc.model.Secretary;
import com.ds3c.tcc.ApiTcc.repository.SecretaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SecretaryService extends CRUDService<Secretary, Long> {
    private final SecretaryMapper secretaryMapper;
    private final SecretaryRepository secretaryRepository;

    @Lazy
    public SecretaryService(
            SecretaryRepository secretaryRepository,
            SecretaryMapper secretaryMapper) {
        super(Secretary.class, secretaryRepository);
        this.secretaryMapper = secretaryMapper;
        this.secretaryRepository = secretaryRepository;
    }

    public Secretary findByUsername(String username) {
        return secretaryRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The secretary with username: "+username+" was not found."));
    }

    public Secretary create(SecretaryRequestDTO dto) {
        return save(secretaryMapper.toEntity(dto));
    }

    public Secretary update(SecretaryRequestDTO dto, Long id) {
        return save(secretaryMapper.updateEntityFromDTO(dto, id));
    }
}
