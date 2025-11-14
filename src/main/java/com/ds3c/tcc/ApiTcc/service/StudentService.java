package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Student.StudentRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.StudentEnroll.StudentEnrollRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import com.ds3c.tcc.ApiTcc.mapper.StudentEnrollMapper;
import com.ds3c.tcc.ApiTcc.mapper.StudentMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.StudentEnroll;
import com.ds3c.tcc.ApiTcc.repository.StudentEnrollRepository;
import com.ds3c.tcc.ApiTcc.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService extends CRUDService<Student, Long> {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SchoolClassService schoolClassService;
    private final BiometryService biometryService;
    private final PresenceLogService presenceLogService;
    private final AttendanceService attendanceService;
    private final LocalStorageService localStorageService;
    private final StudentEnrollMapper studentEnrollMapper;
    private final StudentEnrollRepository studentEnrollRepository;
    private final SupabaseStorageService supabaseStorageService;

    @Lazy
    public StudentService(
            StudentRepository studentRepository,
            StudentMapper studentMapper,
            SchoolClassService schoolClassService,
            BiometryService biometryService,
            PresenceLogService presenceLogService,
            AttendanceService attendanceService,
            LocalStorageService localStorageService,
            StudentEnrollMapper studentEnrollMapper,
            StudentEnrollRepository studentEnrollRepository, SupabaseStorageService supabaseStorageService) {
        super(Student.class, studentRepository);
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.schoolClassService = schoolClassService;
        this.biometryService = biometryService;
        this.presenceLogService = presenceLogService;
        this.attendanceService = attendanceService;
        this.localStorageService = localStorageService;
        this.studentEnrollMapper = studentEnrollMapper;
        this.studentEnrollRepository = studentEnrollRepository;
        this.supabaseStorageService = supabaseStorageService;
    }

    private String generatePassword(StudentRequestDTO dto) {
        return dto.getName().split(" ")[0].toLowerCase()+dto.getRm();
    }

    public Student create(StudentRequestDTO dto) {
        schoolClassService.findById(dto.getSchoolClassId());

        dto.setUsername(dto.getRm().toString());
        dto.setPassword(generatePassword(dto));

        return save(studentMapper.toEntity(dto));
    }

    public Student update(StudentRequestDTO dto, Long id) {
        return save(studentMapper.updateEntityFromDTO(dto, id));
    }

    public Integer findMaxRm() {
        return studentRepository.findMaxRm().orElse(0);
    }

    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("The student with username: "+username+" was not found."));
    }

    public Student findByRm(Integer rm) {
        return studentRepository.findByRm(rm)
                .orElseThrow(() -> new EntityNotFoundException("The student with RM: "+rm+" was not found."));
    }

    public Student findByEnrollId(Long enrollId) {
        return studentRepository.findByEnrollId(enrollId)
                .orElseThrow(() -> new EntityNotFoundException("The student with enroll ID: "+enrollId+" was not found."));
    }

    public Map<String, Object> findFullPresenceLog(Long studentId) {
        Student student = findById(studentId);
        Map<String, Object> presenceLog = new HashMap<>();

        long classesGiven = attendanceService.countClassesGivenForClass(student.getSchoolClass().getId());
        long studentPresences = attendanceService.countStudentPresence(student.getId());
        long studentFrequency = studentPresences * 100 / classesGiven;

        presenceLog.put("classesGiven", classesGiven);
        presenceLog.put("studentPresences", studentPresences);
        presenceLog.put("studentFrequency", studentFrequency);

        return presenceLog;
    }

    public List<Student> findAllBySchoolClass(Long id) {
        return studentRepository.findAllBySchoolClassId(id);
    }

    public List<Student> findAllByStatus(String status) {
        try {
            return studentRepository.findAllByStatus(StatusEnum.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The status: "+status+" is not valid.");
        }
    }

    // Enroll

    public List<StudentEnroll> findAllEnroll() {
        return studentEnrollRepository.findAll();
    }

    public StudentEnroll findEnrollById(Long id) {
        return studentEnrollRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The student enroll with ID: "+id+" was not found."));
    }

    public Map<String, Object> enroll(StudentEnrollRequestDTO dto) throws IOException {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = studentEnrollMapper.toEntity(dto);
        Student student = studentEnrollMapper.toStudent(enroll);

        //String photoUrl = localStorageService.saveFile(
        //        dto.getPhoto(), "/student/"+student.getRm().toString()+"/photo");

        String photoUrl = supabaseStorageService.uploadFile(dto.getPhoto(), "/student/"+student.getRm()+"/profile-picture");

        enroll.setPhotoUrl(photoUrl);
        student.setPhotoUrl(photoUrl);

        studentEnrollRepository.save(enroll);
        save(student);

        response.put("enroll", studentEnrollMapper.toDTO(enroll));
        response.put("student", studentMapper.toDTO(student));

        return response;
    }

    public Map<String, Object> updateEnroll(StudentEnrollRequestDTO dto, Long enrollId) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = studentEnrollMapper.updateEntityFromDTO(dto, enrollId);
        Student student = studentEnrollMapper.toStudent(enroll);

        if (dto.getPhoto() != null) {
            String photoUrl = localStorageService.saveFile(
                    dto.getPhoto(), "/student/"+student.getRm().toString()+"/photo");
            enroll.setPhotoUrl(photoUrl);
            student.setPhotoUrl(photoUrl);
        }

        studentEnrollRepository.save(enroll);
        save(student);

        response.put("enroll", studentEnrollMapper.toDTO(enroll));
        response.put("student", studentMapper.toDTO(student));

        return response;
    }

    public Map<String, Object> setActive(Long id) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = findEnrollById(id);
        Student student = findByEnrollId(id);
        SchoolClass schoolClass = schoolClassService.findById(student.getSchoolClass().getId());

        student.setStatus(StatusEnum.ACTIVE);
        enroll.setStatus(StatusEnum.ACTIVE);
        schoolClass.setStudentsCount(schoolClass.getStudentsCount()+1);

        studentEnrollRepository.save(enroll);
        schoolClassService.save(schoolClass);
        save(student);

        response.put("enroll", studentEnrollMapper.toDTO(enroll));
        response.put("student", studentMapper.toDTO(student));

        return response;
    }

    public Map<String, Object> setInactive(Long id) {
        Map<String, Object> response = new HashMap<>();
        StudentEnroll enroll = findEnrollById(id);
        Student student = findByEnrollId(id);
        SchoolClass schoolClass = schoolClassService.findById(student.getSchoolClass().getId());

        student.setStatus(StatusEnum.INACTIVE);
        enroll.setStatus(StatusEnum.INACTIVE);
        schoolClass.setStudentsCount(schoolClass.getStudentsCount() > 0 ? schoolClass.getStudentsCount()-1 : 0);

        studentEnrollRepository.save(enroll);
        schoolClassService.save(schoolClass);
        save(student);

        response.put("enroll", studentEnrollMapper.toDTO(enroll));
        response.put("student", studentMapper.toDTO(student));

        return response;
    }

    public void setDeleted(Long id) {
        StudentEnroll enroll = findEnrollById(id);
        Student student = findByEnrollId(id);
        SchoolClass schoolClass = schoolClassService.findById(student.getSchoolClass().getId());

        student.setStatus(StatusEnum.DELETED);
        enroll.setStatus(StatusEnum.DELETED);
        schoolClass.setStudentsCount(schoolClass.getStudentsCount() > 0 ? schoolClass.getStudentsCount()-1 : 0);

        studentEnrollRepository.save(enroll);
        schoolClassService.save(schoolClass);
        save(student);
    }

    // Biometry

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
