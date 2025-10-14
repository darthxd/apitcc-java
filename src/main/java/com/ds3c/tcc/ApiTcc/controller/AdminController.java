package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AdminMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.repository.StudentRepository;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service._BiometryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final _BiometryService biometryService;
    private final StudentRepository studentRepository;

    public AdminController(
            AdminService adminService,
            AdminMapper adminMapper,
            _BiometryService biometryService,
            StudentRepository studentRepository) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.biometryService = biometryService;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<AdminResponseDTO> listAdmin() {
        return adminMapper.toListDTO(adminService.listAdmin());
    }

    @PostMapping
    public AdminResponseDTO createAdmin(
            @RequestBody @Valid AdminRequestDTO adminRequestDTO) {
        return adminMapper.toDTO(adminService.createAdmin(adminRequestDTO));
    }

    @GetMapping("/{id}")
    public AdminResponseDTO getAdmin(@PathVariable("id") Long id) {
        return adminMapper.toDTO(adminService.getAdminById(id));
    }

    @GetMapping("/username/{username}")
    public AdminResponseDTO getAdminByUsername(@PathVariable("username") String username) {
        return adminMapper.toDTO(adminService.getAdminByUsername(username));
    }

    @PutMapping("/{id}")
    public AdminResponseDTO updateAdmin(@RequestBody @Valid AdminRequestDTO dto,
                                        @PathVariable("id") Long id) {
        return adminMapper.toDTO(adminService.updateAdmin(dto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

    @PostMapping("/biometry/reset")
    public ResponseEntity<String> resetBiometry() {
        if (!biometryService.resetSensor()) {
            return new ResponseEntity<>(
                    "Error while trying to reset the fingerprint sensor.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        for (Student student : studentRepository.findAll()) {
            student.setBiometry(false);
            studentRepository.save(student);
        }
        return new ResponseEntity<>(
                "All the fingerprints have been cleared.",
                HttpStatus.OK
        );
    }
}
