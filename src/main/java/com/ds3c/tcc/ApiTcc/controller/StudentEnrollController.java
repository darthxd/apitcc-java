package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import com.ds3c.tcc.ApiTcc.mapper.StudentEnrollMapper;
import com.ds3c.tcc.ApiTcc.service.StudentEnrollService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studentenroll")
public class StudentEnrollController {

    private final StudentEnrollService studentEnrollService;
    private final StudentEnrollMapper studentEnrollMapper;

    public StudentEnrollController(
            StudentEnrollService studentEnrollService,
            StudentEnrollMapper studentEnrollMapper) {
        this.studentEnrollService = studentEnrollService;
        this.studentEnrollMapper = studentEnrollMapper;
    }

    @GetMapping
    public List<StudentEnrollResponseDTO> findAll() {
        return studentEnrollService.findAll()
                .stream().map(studentEnrollMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public StudentEnrollResponseDTO findById(@PathVariable("id") Long id) {
        return studentEnrollMapper.toDTO(
                studentEnrollService.findById(id)
        );
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody @Valid StudentEnrollRequestDTO dto) {
        return studentEnrollService.create(dto);
    }

    @PostMapping("/{id}/setactive")
    public Map<String, Object> setActive(@PathVariable("id") Long id) {
        return studentEnrollService.setStatus(id, StatusEnum.ACTIVE.name());
    }

    @PostMapping("/{id}/setinactive")
    public Map<String, Object> setInactive(@PathVariable("id") Long id) {
        return studentEnrollService.setStatus(id, StatusEnum.INACTIVE.name());
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(
            @PathVariable("id") Long id,
            @RequestBody StudentEnrollRequestDTO dto) {
        return studentEnrollService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        return studentEnrollService.setStatus(id, StatusEnum.DELETED.name());
    }
}
