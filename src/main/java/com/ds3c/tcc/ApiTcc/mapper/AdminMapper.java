package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminMapper {
    private final UserService userService;

    public AdminMapper(UserService userService) {
        this.userService = userService;
    }

    public Admin toModel(AdminCreateDTO adminCreateDTO, Long userId) {
        Admin admin = new Admin();
        admin.setCpf(adminCreateDTO.getCpf());
        admin.setEmail(adminCreateDTO.getEmail());
        admin.setName(adminCreateDTO.getName());
        admin.setPhone(adminCreateDTO.getPhone());
        admin.setUserId(userId);
        return admin;
    }

    public AdminResponseDTO toDTO(Admin admin) {
        AdminResponseDTO adminDTO = new AdminResponseDTO();
        UserResponseDTO userDTO = userService.getUserById(admin.getUserId());
        adminDTO.setUsername(userDTO.getUsername());
        adminDTO.setPassword(userDTO.getPassword());
        adminDTO.setCpf(admin.getCpf());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setName(admin.getName());
        adminDTO.setPhone(admin.getPhone());
        return adminDTO;
    }

    public List<AdminResponseDTO> toListDTO(List<Admin> adminList) {
        List<AdminResponseDTO> adminDTOList = new ArrayList<>();
        for(Admin admin : adminList) {
            adminDTOList.add(toDTO(admin));
        }
        return adminDTOList;
    }
}
