package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class AdminMapper {
    private final AdminService adminService;
    private final SchoolUnitService schoolUnitService;
    private final PasswordEncoder passwordEncoder;

    public Admin toEntity(AdminRequestDTO dto) {
        Admin admin = new Admin();
        SchoolUnit unit = schoolUnitService.findById(dto.getUnitId());

        admin.setUsername(dto.getUsername());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(RolesEnum.ROLE_ADMIN);
        admin.setSchoolUnit(unit);

        admin.setName(dto.getName());
        return admin;
    }

    public AdminResponseDTO toDTO(Admin admin) {
        return new AdminResponseDTO(
                admin.getId(),
                admin.getUsername(),
                admin.getPassword(),
                admin.getSchoolUnit() != null ? admin.getSchoolUnit().getId() : null,
                admin.getName()
        );
    }

    public Admin updateEntityFromDTO(AdminRequestDTO dto, Long id) {
        Admin admin = adminService.findById(id);
        if (StringUtils.hasText(dto.getUsername())){
            admin.setUsername(dto.getUsername());
        }
        if (StringUtils.hasText(dto.getPassword())) {
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUnitId() != null) {
            admin.setSchoolUnit(
                    schoolUnitService.findById(dto.getUnitId())
            );
        }
        if (StringUtils.hasText(dto.getName())) {
            admin.setName(dto.getName());
        }
        return admin;
    }
}
