package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkUpdateDTO;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.model.Attendance;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.repository.AttendanceRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AttendanceService extends CRUDService<Attendance, Long> {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Lazy
    public AttendanceService(
            AttendanceRepository attendanceRepository,
            AttendanceMapper attendanceMapper,
            SchoolClassService schoolClassService,
            TeacherService teacherService,
            StudentService studentService) {
        super(Attendance.class, attendanceRepository);
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public List<Attendance> findAllBySchoolClass(Long schoolClassId) {
        return attendanceRepository.findAllBySchoolClassId(schoolClassId);
    }

    public List<Attendance> findAllByDateAndSchoolClass(String date, Long classId) {
        return attendanceRepository.findAllByDateAndSchoolClassId(
                LocalDate.parse(date), classId
        );
    }

    public List<Attendance> findAllByStudent(Long studentId) {
        return attendanceRepository.findAllByStudentId(studentId);
    }

    public Attendance create(AttendanceRequestDTO dto) {
        return save(attendanceMapper.toEntity(dto));
    }

    public Attendance update(AttendanceRequestDTO dto, Long id) {
        return save(attendanceMapper.updateEntityFromDTO(dto, id));
    }

    public List<Attendance> createBulk(AttendanceBulkRequestDTO dto) {
        LocalDate date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        SchoolClass schoolClass = schoolClassService.findById(dto.getSchoolClassId());
        Teacher teacher = teacherService.findById(dto.getTeacherId());

        if (attendanceRepository.existsByDateAndSchoolClassIdAndTeacherId(
                date, dto.getSchoolClassId(), dto.getTeacherId())) {
            throw new IllegalArgumentException("There is already an attendance findAll for this class, teacher and date.");
        }

        List<Attendance> attendanceList = dto.getPresences().stream().map(p -> {
            Student student = studentService.findById(p.getStudentId());
            Attendance attendance = new Attendance();
            attendance.setDate(date);
            attendance.setStudent(student);
            attendance.setSchoolClass(schoolClass);
            attendance.setTeacher(teacher);
            attendance.setPresent(p.getPresent());
            return attendance;
        }).toList();

        return attendanceRepository.saveAll(attendanceList);
    }

    public List<Attendance> updateBulk(AttendanceBulkUpdateDTO dto) {
        List<Attendance> attendanceList = dto.getUpdates().stream().map(update -> {
            Attendance attendance = findById(update.getAttendanceId());
            if (update.getPresent() != null) {
                attendance.setPresent(update.getPresent());
            }
            return attendance;
        }).toList();

        return attendanceRepository.saveAll(attendanceList);
    }

    public long countClassesGivenForClass(Long schoolClassId) {
        return attendanceRepository.countClassesGivenForClass(schoolClassId);
    }

    public long countStudentPresence(Long studentId) {
        return attendanceRepository.countStudentPresence(studentId);
    }

    public long countClassesGivenForClassAndSubject(Long schoolClassId, Long subjectId) {
        return attendanceRepository.countClassesGivenForClassAndSubject(schoolClassId, subjectId);
    }

    public long countStudentPresencesForSubject(Long studentId, Long subjectId) {
        return attendanceRepository.countStudentPresentForSubject(studentId, subjectId);
    }
}
