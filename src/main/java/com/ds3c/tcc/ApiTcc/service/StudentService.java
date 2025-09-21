package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.exception.BiometryRegisterException;
import com.ds3c.tcc.ApiTcc.exception.StudentNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final SchoolClassService schoolClassService;
    private final BiometryService biometryService;

    @Autowired
    @Lazy
    public StudentService(
            StudentRepository studentRepository,
            StudentMapper studentMapper,
            UserService userService,
            SchoolClassService schoolClassService, BiometryService biometryService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.schoolClassService = schoolClassService;
        this.biometryService = biometryService;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUserId(
                userService.getUserByUsername(username).getId())
                .orElseThrow(() -> new StudentNotFoundException(username));
    }

    public String generateStudentUsername(StudentRequestDTO dto) {
        return dto.getName().split(" ")[0].toLowerCase()+"."
                +dto.getName().split(" ")[
                        dto.getName().split(" ").length-1
                ].toLowerCase();
    }

    public String generateStudentPassword(StudentRequestDTO dto) {
        return dto.getName().split(" ")[0].toLowerCase()+dto.getRm();
    }

    public List<Student> listStudent() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentRequestDTO dto) {
        schoolClassService.getSchoolClassById(dto.getSchoolClassId());
        dto.setUsername(generateStudentUsername(dto));
        dto.setPassword(generateStudentPassword(dto));
        User user = userService.createUser(dto, RolesEnum.ROLE_STUDENT);
        Student student = studentMapper.toModel(dto, user.getId());
        return studentRepository.save(student);
    }

    public Student updateStudent(StudentRequestDTO dto,
                                 Long id) {
        return studentRepository.save(
                studentMapper.updateModelFromDTO(dto, id)
        );
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        userService.deleteUser(student.getUserId());
        studentRepository.delete(student);
    }

    public List<Student> listStudentFromSchoolClass(Long id) {
        return studentRepository.findAllBySchoolClassId(id);
    }

    public ResponseEntity<String> enrollBiometry(BiometryRequestDTO dto) {
        Student student = getStudentById(dto.getStudentId());
        if(biometryService.enrollFingerPrint(dto.getStudentId())) {
            student.setBiometry(true);
            studentRepository.save(student);
            return new ResponseEntity<>(
                    "The biometry for the student with ID: "+student.getId()+" was registered.",
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                "Error while registering the biometry.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public Student readPresence() {
        Student student = biometryService.readFingerprint()
                .orElseThrow(() -> (new BiometryRegisterException()));
        student.setInschool(!student.getInschool());
        studentRepository.save(student);
        return student;
    }
}
