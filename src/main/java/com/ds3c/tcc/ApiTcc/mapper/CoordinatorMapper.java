package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.Coordinator;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.service.CoordinatorService;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class CoordinatorMapper {
    private final PasswordEncoder passwordEncoder;
    private final SchoolUnitService schoolUnitService;
    private final CoordinatorService coordinatorService;

    public Coordinator toEntity(CoordinatorRequestDTO dto) {
        Coordinator coordinator = new Coordinator();
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());

        coordinator.setUsername(dto.getUsername());
        coordinator.setPassword(passwordEncoder.encode(dto.getPassword()));
        coordinator.setRole(RolesEnum.ROLE_COORDINATOR);
        coordinator.setSchoolUnit(unit);

        coordinator.setName(dto.getName());
        coordinator.setCpf(dto.getCpf());
        coordinator.setEmail(dto.getEmail());
        coordinator.setPhone(dto.getPhone());

        return coordinator;
    }

    public CoordinatorResponseDTO toDTO(Coordinator coordinator) {
        return new CoordinatorResponseDTO(
                coordinator.getId(),
                coordinator.getUsername(),
                coordinator.getPassword(),
                coordinator.getSchoolUnit().getId(),
                coordinator.getName(),
                coordinator.getCpf(),
                coordinator.getEmail(),
                coordinator.getPhone()
        );
    }

    public  Coordinator updateEntityFromDTO(CoordinatorRequestDTO dto, Long id) {
        Coordinator coordinator = coordinatorService.findById(id);

        if (StringUtils.hasText(dto.getUsername())) {
            coordinator.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            coordinator.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            coordinator.setSchoolUnit(
                    schoolUnitService.findById(dto.getUnitId())
            );
        }
        if (StringUtils.hasText(dto.getName())) {
            coordinator.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getCpf())) {
            coordinator.setCpf(dto.getCpf());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            coordinator.setEmail(dto.getEmail());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            coordinator.setPhone(dto.getPhone());
        }
        return coordinator;
    }
}
