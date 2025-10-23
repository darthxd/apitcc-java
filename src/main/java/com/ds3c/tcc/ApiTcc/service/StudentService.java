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
public class StudentService extends CRUDService<Student, Long> {
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
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.schoolClassService = schoolClassService;
        this.biometryService = biometryService;
        this.presenceLogService = presenceLogService;
    }

    private String generatePassword(StudentRequestDTO dto) {
        return dto.getName().split(" ")[0].toLowerCase()+dto.getRm();
    }

    public Student findByUsername(String username) {
        return studentRepository.findByUserId(
                userService.findByUsername(username).getId())
                .orElseThrow(() -> new StudentNotFoundException(username));
    }

    public Student create(StudentRequestDTO dto) {
        schoolClassService.findById(dto.getSchoolClassId());
        dto.setUsername(dto.getRm());
        dto.setPassword(generatePassword(dto));
        User user = userService.create(dto, RolesEnum.ROLE_STUDENT, dto.getUnitId());
        Student student = studentMapper.toEntity(dto, user.getId());
        return save(student);
    }

    public Student update(StudentRequestDTO dto, Long id) {
        return save(studentMapper.updateEntityFromDTO(dto, id));
    }

    public List<Student> findAllBySchoolClass(Long id) {
        return studentRepository.findAllBySchoolClassId(id);
    }

    public ResponseEntity<String> enrollBiometry(BiometryRequestDTO dto) {
        Student student = findById(dto.getStudentId());
        if(biometryService.enroll(dto.getStudentId())) {
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
        Student student = biometryService.read()
                .orElseThrow(() -> (new RuntimeException("No corresponding biometry was found.")));
        presenceLogService.togglePresence(student.getId());
        return student;
    }

    public ResponseEntity<String> deleteBiometry(BiometryRequestDTO dto) {
        if (!biometryService.delete(dto.getStudentId())) {
            return new ResponseEntity<>(
                    "Error while trying to delete the biometry.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        Student student = findById(dto.getStudentId());
        student.setBiometry(false);
        student.setInschool(false);
        studentRepository.save(student);
        return new ResponseEntity<>(
                "The biometry for the student with ID: "+dto.getStudentId()+" was deleted.",
                HttpStatus.OK
        );
    }

    public void setInSchool(Student student, Boolean inschool) {
        student.setInschool(inschool);
        studentRepository.save(student);
    }
}
