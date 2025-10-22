package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AdminMapper {
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    @Lazy
    public AdminMapper(
            UserService userService,
            AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    public Admin toEntity(AdminRequestDTO adminRequestDTO, Long userId) {
        Admin admin = new Admin();
        admin.setCpf(adminRequestDTO.getCpf());
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setName(adminRequestDTO.getName());
        admin.setPhone(adminRequestDTO.getPhone());
        admin.setUserId(userId);
        return admin;
    }

    public AdminResponseDTO toDTO(Admin admin) {
        AdminResponseDTO adminDTO = new AdminResponseDTO();
        User user = userService.getById(admin.getUserId());

        return new AdminResponseDTO(
                admin.getId(),
                user.getUsername(),
                user.getPassword(),
                admin.getName(),
                admin.getEmail(),
                admin.getCpf(),
                admin.getPhone(),
                user.getSchoolUnit().getId()
        );
    }

    public Admin updateEntityFromDTO(AdminRequestDTO adminRequestDTO, Long id) {
        Admin admin = adminService.getById(id);
        if (StringUtils.hasText(adminRequestDTO.getUsername())
                || StringUtils.hasText(adminRequestDTO.getPassword())) {
            userService.update(adminRequestDTO, admin.getUserId());
        }
        if (StringUtils.hasText(adminRequestDTO.getName())) {
            admin.setName(adminRequestDTO.getName());
        }
        if (StringUtils.hasText(adminRequestDTO.getCpf())) {
            admin.setCpf(adminRequestDTO.getCpf());
        }
        if (StringUtils.hasText(adminRequestDTO.getPhone())) {
            admin.setPhone(adminRequestDTO.getPhone());
        }
        if (StringUtils.hasText(adminRequestDTO.getEmail())) {
            admin.setEmail(adminRequestDTO.getEmail());
        }
        return admin;
    }
}
