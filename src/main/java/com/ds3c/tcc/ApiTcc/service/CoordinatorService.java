package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.CoordinatorMapper;
import com.ds3c.tcc.ApiTcc.model.Coordinator;
import com.ds3c.tcc.ApiTcc.repository.CoordinatorRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService extends CRUDService<Coordinator, Long>{
    private final CoordinatorMapper coordinatorMapper;

    @Lazy
    public CoordinatorService(
            CoordinatorRepository coordinatorRepository,
            CoordinatorMapper coordinatorMapper) {
        super(Coordinator.class, coordinatorRepository);
        this.coordinatorMapper = coordinatorMapper;
    }

    public Coordinator create(CoordinatorRequestDTO dto) {
        return save(coordinatorMapper.toEntity(dto));
    }

    public Coordinator update(CoordinatorRequestDTO dto, Long id) {
        return save(coordinatorMapper.updateEntityFromDTO(dto, id));
    }
}
