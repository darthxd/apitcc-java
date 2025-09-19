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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @Autowired
    @Lazy
    public StudentController(StudentMapper studentMapper,
                             StudentService studentService) {
        this.studentMapper = studentMapper;
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> listStudent() {
        return studentMapper.toListDTO(
                studentService.listStudent()
        );
    }

    @PostMapping
    public StudentResponseDTO createStudent(@RequestBody @Valid StudentRequestDTO dto) {
        return studentMapper.toDTO(
                studentService.createStudent(dto)
        );
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getStudentById(@PathVariable("id") Long id) {
        return studentMapper.toDTO(
                studentService.getStudentById(id)
        );
    }

    @GetMapping("/username/{username}")
    public StudentResponseDTO getStudentByUsername(@PathVariable("username") String username) {
        return studentMapper.toDTO(
                studentService.getStudentByUsername(username)
        );
    }

    @PutMapping("/{id}")
    public StudentResponseDTO updateStudent(@RequestBody @Valid StudentRequestDTO dto,
                                            @PathVariable("id") Long id) {
        return studentMapper.toDTO(
                studentService.updateStudent(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/biometry/create")
    public String registerBiometry(@RequestBody @Valid BiometryRequestDTO dto) {
        return studentService.registerBiometry(dto);
    }

    @PostMapping("/biometry/present")
    public StudentResponseDTO readPresence(@RequestBody @Valid BiometryRequestDTO dto) {
        Student student = studentService.readPresence(dto);
        return studentMapper.toDTO(student);
    }
}
