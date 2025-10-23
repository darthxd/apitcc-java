package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.exception.AdminNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.AdminMapper;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserService userService;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    @Autowired
    @Lazy
    public AdminService(
            UserService userService,
            AdminMapper adminMapper,
            AdminRepository adminRepository) {
        this.userService = userService;
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }

    public Admin create(AdminRequestDTO dto) {
        User user = userService.create(dto, RolesEnum.ROLE_ADMIN, dto.getUnitId());
        Admin admin = adminMapper.toEntity(dto, user.getId());
        return adminRepository.save(admin);
    }

    public Admin getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
    }

    public Admin getByUsername(String username) {
        return adminRepository.findByUserId(
                userService.findByUsername(username).getId())
                .orElseThrow(() -> new AdminNotFoundException(username));
    }

    public List<Admin> list() {
        return adminRepository.findAll();
    }

    public Admin update(AdminRequestDTO dto,
                        Long id) {
        return adminRepository.save(
                adminMapper.updateEntityFromDTO(dto, id)
        );
    }

    public void delete(Long id) {
        Admin admin = getById(id);
        userService.delete(userService.findById(admin.getUserId()));
        adminRepository.delete(admin);
    }
}
