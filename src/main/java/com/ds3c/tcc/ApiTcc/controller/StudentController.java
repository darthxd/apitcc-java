package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @Autowired
    @Lazy
    public StudentController(
            StudentMapper studentMapper,
            StudentService studentService) {
        this.studentMapper = studentMapper;
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> list() {
        return studentService.list().stream().map(studentMapper::toDTO).toList();
    }

    @PostMapping
    public StudentResponseDTO create(@RequestBody @Valid StudentRequestDTO dto) {
        return studentMapper.toDTO(
                studentService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable("id") Long id) {
        return studentMapper.toDTO(
                studentService.getById(id)
        );
    }

    @GetMapping("/username/{username}")
    public StudentResponseDTO getByUsername(@PathVariable("username") String username) {
        return studentMapper.toDTO(
                studentService.getByUsername(username)
        );
    }

    @PutMapping("/{id}")
    public StudentResponseDTO update(@RequestBody @Valid StudentRequestDTO dto,
                                            @PathVariable("id") Long id) {
        return studentMapper.toDTO(
                studentService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        studentService.delete(id);
    }

    @PostMapping("/biometry/enroll")
    public ResponseEntity<String> registerBiometry(@RequestBody @Valid BiometryRequestDTO dto) {
        return studentService.enrollBiometry(dto);
    }

    @PostMapping("/biometry/read")
    public StudentResponseDTO readPresence() {
        Student student = studentService.readPresence();
        return studentMapper.toDTO(student);
    }

    @PostMapping("/biometry/delete")
    public ResponseEntity<String> deleteBiometry(@RequestBody @Valid BiometryRequestDTO dto) {
        return studentService.deleteBiometry(dto);
    }
}
