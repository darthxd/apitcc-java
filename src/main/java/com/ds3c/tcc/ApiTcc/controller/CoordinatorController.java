package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Coordinator.CoordinatorResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.CoordinatorMapper;
import com.ds3c.tcc.ApiTcc.service.CoordinatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinator")
public class CoordinatorController {
    private final CoordinatorService coordinatorService;
    private final CoordinatorMapper coordinatorMapper;

    public CoordinatorController(
            CoordinatorService coordinatorService,
            CoordinatorMapper coordinatorMapper) {
        this.coordinatorService = coordinatorService;
        this.coordinatorMapper = coordinatorMapper;
    }

    @GetMapping
    public List<CoordinatorResponseDTO> findAll() {
        return coordinatorService.findAll().stream()
                .map(coordinatorMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public CoordinatorResponseDTO findById(@PathVariable("id") Long id) {
        return coordinatorMapper.toDTO(coordinatorService.findById(id));
    }

    @GetMapping("/username/{username}")
    public CoordinatorResponseDTO findByUsername(@PathVariable("username") String username) {
        return coordinatorMapper.toDTO(coordinatorService.findByUsername(username));
    }

    @PostMapping
    public CoordinatorResponseDTO create(@RequestBody @Valid CoordinatorRequestDTO dto) {
        return coordinatorMapper.toDTO(coordinatorService.create(dto));
    }

    @PutMapping("/{id}")
    public CoordinatorResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody @Valid CoordinatorRequestDTO dto) {
        return coordinatorMapper.toDTO(coordinatorService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        coordinatorService.delete(coordinatorService.findById(id));
    }
}
