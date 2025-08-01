package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminDTO;
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
    public List<AdminDTO> listAdmin() {
        return adminService.listAdmin();
    }

    @PostMapping
    public AdminDTO createAdmin(
            @RequestBody @Valid AdminCreateDTO adminCreateDTO) {
        return adminService.createAdmin(adminCreateDTO);
    }

    @GetMapping("/{id}")
    public AdminDTO getAdmin(@PathVariable("id") Long id) {
        return adminService.getAdminById(id);
    }
}
