package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
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
    private final PresenceLogService presenceLogService;

    @Autowired
    @Lazy
    public StudentService(
            StudentRepository studentRepository,
            StudentMapper studentMapper,
            UserService userService,
            SchoolClassService schoolClassService,
            BiometryService biometryService,
            PresenceLogService presenceLogService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.schoolClassService = schoolClassService;
        this.biometryService = biometryService;
        this.presenceLogService = presenceLogService;
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
        Student student = studentMapper.toEntity(dto, user.getId());
        return studentRepository.save(student);
    }

    public Student updateStudent(StudentRequestDTO dto,
                                 Long id) {
        return studentRepository.save(
                studentMapper.updateEntityFromDTO(dto, id)
        );
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        userService.deleteUser(student.getUserId());
        studentRepository.delete(student);
        biometryService.deleteFingerprint(id);
    }

    public List<Student> listStudentFromSchoolClass(Long id) {
        return studentRepository.findAllBySchoolClassId(id);
    }

    public ResponseEntity<String> enrollBiometry(BiometryRequestDTO dto) {
        Student student = getStudentById(dto.getStudentId());
        if(biometryService.enrollFingerprint(dto.getStudentId())) {
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
                .orElseThrow(() -> (new RuntimeException("No corresponding biometry was found.")));
        presenceLogService.togglePresence(student.getId());
        return student;
    }

    public ResponseEntity<String> deleteBiometry(BiometryRequestDTO dto) {
        if (!biometryService.deleteFingerprint(dto.getStudentId())) {
            return new ResponseEntity<>(
                    "Error while trying to delete the biometry.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        Student student = getStudentById(dto.getStudentId());
        student.setBiometry(false);
        student.setInschool(false);
        studentRepository.save(student);
        return new ResponseEntity<>(
                "The biometry for the student with ID: "+dto.getStudentId()+" was deleted.",
                HttpStatus.OK
        );
    }

    public Student setInSchool(Student student, Boolean inschool) {
        student.setInschool(inschool);
        return studentRepository.save(student);
    }
}
