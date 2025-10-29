package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import com.ds3c.tcc.ApiTcc.mapper.StudentEnrollMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.StudentEnroll;
import com.ds3c.tcc.ApiTcc.repository.StudentEnrollRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentEnrollService extends CRUDService<StudentEnroll, Long> {
    private final StudentEnrollMapper studentEnrollMapper;
    private final StudentService studentService;

    @Lazy
    public StudentEnrollService(
            StudentEnrollRepository studentEnrollRepository,
            StudentEnrollMapper studentEnrollMapper, StudentService studentService) {
        super(StudentEnroll.class, studentEnrollRepository);
        this.studentEnrollMapper = studentEnrollMapper;
        this.studentService = studentService;
    }

    public Map<String, Object> create(StudentEnrollRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = studentEnrollMapper.toEntity(dto);
        Student student = studentEnrollMapper.toStudent(enroll);

        save(enroll);
        studentService.save(student);

        response.put("enroll", enroll);
        response.put("student", student);

        return response;
    }

    public Map<String, Object> update(StudentEnrollRequestDTO dto, Long enrollId) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = studentEnrollMapper.updateEntityFromDTO(dto, enrollId);
        Student student = studentEnrollMapper.toStudent(enroll);

        save(enroll);
        studentService.save(student);

        response.put("enroll", enroll);
        response.put("student", student);

        return response;
    }

    public Map<String, Object> setStatus(Long enrollId, String status) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = findById(enrollId);
        Student student = studentService.findByEnrollId(enrollId);

        try {
            enroll.setStatus(StatusEnum.valueOf(status.toUpperCase()));
            student.setStatus(StatusEnum.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The status: "+status+" is not valid.");
        }

        save(enroll);
        studentService.save(student);

        response.put("enroll", enroll);
        response.put("student", student);

        return response;
    }
}
