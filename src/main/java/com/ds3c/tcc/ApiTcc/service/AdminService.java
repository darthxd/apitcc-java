package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.AdminMapper;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends CRUDService<Admin, Long> {
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    public AdminService(
            AdminMapper adminMapper,
            AdminRepository adminRepository) {
        super(Admin.class, adminRepository);
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The admin with username: "+username+" was not found."));
    }

    public Admin create(AdminRequestDTO dto) {
        Admin admin = adminMapper.toEntity(dto);
        return adminRepository.save(admin);
    }

    public Admin update(AdminRequestDTO dto,
                        Long id) {
        Admin admin = findById(id);
        return adminRepository.save(
                adminMapper.updateEntityFromDTO(dto, admin)
        );
    }
}
