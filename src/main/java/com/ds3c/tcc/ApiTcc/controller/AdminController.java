package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<AdminResponseDTO> listAdmin() {
        return adminService.listAdmin();
    }

    @PostMapping
    public AdminResponseDTO createAdmin(
            @RequestBody @Valid AdminCreateDTO adminCreateDTO) {
        return adminService.createAdmin(adminCreateDTO);
    }

    @GetMapping("/{id}")
    public AdminResponseDTO getAdmin(@PathVariable("id") Long id) {
        return adminService.getAdminById(id);
    }
}
