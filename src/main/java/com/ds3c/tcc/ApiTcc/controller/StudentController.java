package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class StudentController {

    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @GetMapping
    public List<StudentResponseDTO> findAll() {
        return studentService.findAll().stream().map(studentMapper::toDTO).toList();
    }

    @PostMapping
    public StudentResponseDTO create(@RequestBody @Valid StudentRequestDTO dto) {
        return studentMapper.toDTO(
                studentService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public StudentResponseDTO findById(@PathVariable("id") Long id) {
        return studentMapper.toDTO(
                studentService.findById(id)
        );
    }

    @GetMapping("/username/{username}")
    public StudentResponseDTO findByUsername(@PathVariable("username") String username) {
        return studentMapper.toDTO(
                studentService.findByUsername(username)
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
        studentService.delete(studentService.findById(id));
    }

    @GetMapping("/{id}/presencelog")
    public Map<String, Object> findFullPresenceLog(@PathVariable("id") Long id) {
        return studentService.findFullPresenceLog(id);
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
