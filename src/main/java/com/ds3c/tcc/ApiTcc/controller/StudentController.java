package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.StudentEnrollMapper;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentMapper studentMapper;
    private final StudentService studentService;
    private final StudentEnrollMapper studentEnrollMapper;
    private final AttendanceService attendanceService;

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
        studentService.setDeleted(id);
    }

    @GetMapping("/{id}/presencelog")
    public Map<String, Object> findFullPresenceLog(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return attendanceService.findFullPresenceLog(student);
    }

    // Biometry

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

    // Student Enroll

    @GetMapping("/enroll")
    public List<StudentEnrollResponseDTO> findAllEnroll() {
        return studentService.findAllEnroll().stream()
                .map(studentEnrollMapper::toDTO)
                .toList();
    }

    @GetMapping("/enroll/{id}")
    public StudentEnrollResponseDTO findEnrollById(@PathVariable("id") Long id) {
        return studentEnrollMapper.toDTO(studentService.findEnrollById(id));
    }

    @PostMapping(value = "/enroll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> enroll(
            @ModelAttribute StudentEnrollRequestDTO dto) throws IOException {
        return studentService.enroll(dto);
    }

    @PostMapping("/{id}/setactive")
    public Map<String, Object> setEnrollActive(@PathVariable("id") Long id) {
        return studentService.setActive(id);
    }

    @PostMapping("/{id}/setinactive")
    public Map<String, Object> setEnrollInactive(@PathVariable("id") Long id) {
        return studentService.setInactive(id);
    }

    @PutMapping("/enroll/{id}")
    public Map<String, Object> updateEnroll(
            @PathVariable("id") Long id,
            @RequestBody StudentEnrollRequestDTO dto) {
        return studentService.updateEnroll(dto, id);
    }
}
