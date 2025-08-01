package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserDTO;
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

    public AdminDTO toDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        UserDTO userDTO = userService.getUserById(admin.getUserId());
        adminDTO.setUsername(userDTO.getUsername());
        adminDTO.setPassword(userDTO.getPassword());
        adminDTO.setCpf(admin.getCpf());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setName(admin.getName());
        adminDTO.setPhone(admin.getPhone());
        return adminDTO;
    }

    public List<AdminDTO> toListDTO(List<Admin> adminList) {
        List<AdminDTO> adminDTOList = new ArrayList<>();
        for(Admin admin : adminList) {
            adminDTOList.add(toDTO(admin));
        }
        return adminDTOList;
    }
}
