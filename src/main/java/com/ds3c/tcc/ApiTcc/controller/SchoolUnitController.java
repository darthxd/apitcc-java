package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolUnit.SchoolUnitResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolUnitMapper;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schoolunit")
public class SchoolUnitController {
    private final SchoolUnitService schoolUnitService;
    private final SchoolUnitMapper schoolUnitMapper;

    public SchoolUnitController(
            SchoolUnitService schoolUnitService,
            SchoolUnitMapper schoolUnitMapper) {
        this.schoolUnitService = schoolUnitService;
        this.schoolUnitMapper = schoolUnitMapper;
    }

    @GetMapping
    public List<SchoolUnitResponseDTO> findAll() {
        return schoolUnitService.findAll()
                .stream().map(schoolUnitMapper::toDTO).toList();
    }

    @PostMapping
    public SchoolUnitResponseDTO create(@RequestBody @Valid SchoolUnitRequestDTO dto) {
        return schoolUnitMapper.toDTO(schoolUnitService.create(dto));
    }
}
