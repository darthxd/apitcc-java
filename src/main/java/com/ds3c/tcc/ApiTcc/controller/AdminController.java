package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Admin.AdminResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AdminMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.repository.StudentRepository;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service.BiometryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final BiometryService biometryService;
    private final StudentRepository studentRepository;

    @GetMapping
    public List<AdminResponseDTO> findAll() {
        return adminService.findAll().stream().map(adminMapper::toDTO).toList();
    }

    @PostMapping
    public AdminResponseDTO create(
            @RequestBody @Valid AdminRequestDTO adminRequestDTO) {
        return adminMapper.toDTO(adminService.create(adminRequestDTO));
    }

    @GetMapping("/{id}")
    public AdminResponseDTO findById(@PathVariable("id") Long id) {
        return adminMapper.toDTO(adminService.findById(id));
    }

    @GetMapping("/username/{username}")
    public AdminResponseDTO findByUsername(@PathVariable("username") String username) {
        return adminMapper.toDTO(adminService.findByUsername(username));
    }

    @PutMapping("/{id}")
    public AdminResponseDTO update(@RequestBody @Valid AdminRequestDTO dto,
                                        @PathVariable("id") Long id) {
        return adminMapper.toDTO(adminService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminService.delete(adminService.findById(id));
    }

    @PostMapping("/biometry/reset")
    public ResponseEntity<String> resetBiometry() {
        if (!biometryService.reset()) {
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
