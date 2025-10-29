package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Secretary;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import com.ds3c.tcc.ApiTcc.service.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class SecretaryMapper {
    private final SchoolUnitService schoolUnitService;
    private final SecretaryService secretaryService;
    private final PasswordEncoder passwordEncoder;

    public Secretary toEntity(SecretaryRequestDTO dto) {
        Secretary secretary = new Secretary();
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());

        secretary.setUsername(dto.getUsername());
        secretary.setPassword(passwordEncoder.encode(dto.getPassword()));
        secretary.setSchoolUnit(unit);
        secretary.setRole(RolesEnum.ROLE_SECRETARY);
        secretary.setEmail(dto.getEmail());
        secretary.setPhone(dto.getPhone());

        return secretary;
    }

    public SecretaryResponseDTO toDTO(Secretary secretary) {
        return new SecretaryResponseDTO(
                secretary.getId(),
                secretary.getUsername(),
                secretary.getPassword(),
                secretary.getSchoolUnit().getId(),
                secretary.getEmail(),
                secretary.getPhone()
        );
    }

    public Secretary updateEntityFromDTO(SecretaryRequestDTO dto, Long id) {
        Secretary secretary = secretaryService.findById(id);

        if (StringUtils.hasText(dto.getUsername())) {
            secretary.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            secretary.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            secretary.setSchoolUnit(
                    schoolUnitService.findById(dto.getUnitId())
            );
        }
        if (StringUtils.hasText(dto.getEmail())) {
            secretary.setEmail(dto.getEmail());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            secretary.setPhone(dto.getPhone());
        }
        return secretary;
    }
}
