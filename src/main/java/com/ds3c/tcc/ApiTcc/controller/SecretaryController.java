package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Secretary.SecretaryResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.SecretaryMapper;
import com.ds3c.tcc.ApiTcc.service.SecretaryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secretary")
public class SecretaryController {
    private final SecretaryService secretaryService;
    private final SecretaryMapper secretaryMapper;

    public SecretaryController(
            SecretaryService secretaryService,
            SecretaryMapper secretaryMapper) {
        this.secretaryService = secretaryService;
        this.secretaryMapper = secretaryMapper;
    }

    @GetMapping
    public List<SecretaryResponseDTO> findAll() {
        return secretaryService.findAll().stream()
                .map(secretaryMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public SecretaryResponseDTO findById(@PathVariable("id") Long id) {
        return secretaryMapper.toDTO(secretaryService.findById(id));
    }

    @GetMapping("/username/{username}")
    public SecretaryResponseDTO findByUsername(@PathVariable("username") String username) {
        return secretaryMapper.toDTO(secretaryService.findByUsername(username));
    }

    @PostMapping
    public SecretaryResponseDTO create(@RequestBody @Valid SecretaryRequestDTO dto) {
        return secretaryMapper.toDTO(secretaryService.create(dto));
    }

    @PutMapping("/{id}")
    public SecretaryResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody @Valid SecretaryRequestDTO dto) {
        return secretaryMapper.toDTO(secretaryService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        secretaryService.delete(secretaryService.findById(id));
    }
}
