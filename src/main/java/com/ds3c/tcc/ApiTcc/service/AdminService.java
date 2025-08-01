package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminDTO;
import com.ds3c.tcc.ApiTcc.exception.AdminNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.AdminMapper;
import com.ds3c.tcc.ApiTcc.mapper.UserMapper;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    public AdminService(UserService userService,
                        UserMapper userMapper,
                        AdminMapper adminMapper,
                        AdminRepository adminRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }

    public AdminDTO createAdmin(AdminCreateDTO adminCreateDTO) {
        User user = userService.createUserByAdmin(adminCreateDTO);
        Admin admin = adminMapper.toModel(adminCreateDTO, user.getId());
        return adminMapper.toDTO(adminRepository.save(admin));
    }

    public List<AdminDTO> listAdmin() {
        return adminMapper.toListDTO(adminRepository.findAll());
    }

    public AdminDTO getAdminById(Long id) {
        return adminMapper.toDTO(adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id)));
    }
}
