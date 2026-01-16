package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.Secretary;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class SecretaryMapper {
    private final PasswordEncoder passwordEncoder;

    public Secretary toEntity(SecretaryRequestDTO dto, SchoolUnit unit) {
        Secretary secretary = new Secretary();

        secretary.setUsername(dto.getUsername());
        secretary.setPassword(passwordEncoder.encode(dto.getPassword()));
        secretary.setRole(RolesEnum.ROLE_SECRETARY);
        secretary.setSchoolUnit(unit);

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
                secretary.getSchoolUnit().getName(),
                secretary.getEmail(),
                secretary.getPhone()
        );
    }

    public Secretary updateEntityFromDTO(SecretaryRequestDTO dto, Secretary secretary, SchoolUnit unit) {

        if (StringUtils.hasText(dto.getUsername())) {
            secretary.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            secretary.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            secretary.setSchoolUnit(unit);
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
