package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.CoordinatorMapper;
import com.ds3c.tcc.ApiTcc.model.Coordinator;
import com.ds3c.tcc.ApiTcc.repository.CoordinatorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService extends CRUDService<Coordinator, Long>{
    private final CoordinatorMapper coordinatorMapper;
    private final CoordinatorRepository coordinatorRepository;

    public CoordinatorService(
            CoordinatorRepository coordinatorRepository,
            CoordinatorMapper coordinatorMapper) {
        super(Coordinator.class, coordinatorRepository);
        this.coordinatorMapper = coordinatorMapper;
        this.coordinatorRepository = coordinatorRepository;
    }

    public Coordinator findByUsername(String username) {
        return coordinatorRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The coordinator with username: "+username+" was not found."));
    }

    public Coordinator create(CoordinatorRequestDTO dto) {
        return save(coordinatorMapper.toEntity(dto));
    }

    public Coordinator update(CoordinatorRequestDTO dto, Long id) {
        Coordinator coordinator = findById(id);
        return save(coordinatorMapper.updateEntityFromDTO(dto, coordinator));
    }
}
