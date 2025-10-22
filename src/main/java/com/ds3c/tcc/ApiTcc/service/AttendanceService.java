package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkUpdateDTO;
import com.ds3c.tcc.ApiTcc.exception.AttendanceNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.model.Attendance;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    @Lazy
    public AttendanceService(
            AttendanceRepository attendanceRepository,
            AttendanceMapper attendanceMapper,
            SchoolClassService schoolClassService,
            TeacherService teacherService,
            StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public Attendance getById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(id));
    }

    public List<Attendance> listBySchoolClass(Long schoolClassId) {
        return attendanceRepository.findAllBySchoolClassId(schoolClassId);
    }

    public List<Attendance> list() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> listByDateAndSchoolClass(String date, Long classId) {
        return attendanceRepository.findAllByDateAndSchoolClassId(
                LocalDate.parse(date), classId
        );
    }

    public List<Attendance> listByStudent(Long studentId) {
        return attendanceRepository.findAllByStudentId(studentId);
    }

    public Attendance create(AttendanceRequestDTO dto) {
        return attendanceRepository.save(
                attendanceMapper.toEntity(dto)
        );
    }

    public Attendance update(AttendanceRequestDTO dto, Long id) {
        return attendanceRepository.save(
                attendanceMapper.updateEntityFromDTO(dto, id)
        );
    }

    public List<Attendance> createBulk(AttendanceBulkRequestDTO dto) {
        LocalDate date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        SchoolClass schoolClass = schoolClassService.getById(dto.getSchoolClassId());
        Teacher teacher = teacherService.getById(dto.getTeacherId());

        if (attendanceRepository.existsByDateAndSchoolClassIdAndTeacherId(
                date, dto.getSchoolClassId(), dto.getTeacherId())) {
            throw new IllegalArgumentException("There is already an attendance list for this class, teacher and date.");
        }

        List<Attendance> attendanceList = dto.getPresences().stream().map(p -> {
            Student student = studentService.getById(p.getStudentId());
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
            Attendance attendance = getById(update.getAttendanceId());
            if (update.getPresent() != null) {
                attendance.setPresent(update.getPresent());
            }
            return attendance;
        }).toList();

        return attendanceRepository.saveAll(attendanceList);
    }

    public void delete(Long id) {
        Attendance attendance = getById(id);
        attendanceRepository.delete(attendance);
    }
}
