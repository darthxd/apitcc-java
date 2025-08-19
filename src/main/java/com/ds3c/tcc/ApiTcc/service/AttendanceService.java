package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    @Lazy
    public AttendanceService(AttendanceRepository attendanceRepository,
                             AttendanceMapper attendanceMapper, SchoolClassService schoolClassService, TeacherService teacherService, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(id));
    }

    public List<Attendance> listAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> listAttendanceByDateAndClassId(String date, Long classId) {
        return attendanceRepository.findAllByDateAndSchoolClassId(
                LocalDate.parse(date), classId
        );
    }

    public Attendance createAttendance(AttendanceRequestDTO dto) {
        return attendanceRepository.save(
                attendanceMapper.toModel(dto)
        );
    }

    public List<Attendance> createAttendanceBulk(AttendanceBulkRequestDTO dto) {
        LocalDate date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        SchoolClass schoolClass = schoolClassService.getSchoolClassById(dto.getSchoolClassId());
        Teacher teacher = teacherService.getTeacherById(dto.getTeacherId());

        if (attendanceRepository.existsByDateAndSchoolClassIdAndTeacherId(
                date, dto.getSchoolClassId(), dto.getTeacherId())) {
            throw new IllegalArgumentException("There is already an attendance list for this class, teacher and date.");
        }

        List<Attendance> attendanceList = dto.getPresences().stream().map(p -> {
            Student student = studentService.getStudentById(p.getStudentId());
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

    public Attendance updateAttendance(AttendanceRequestDTO dto, Long id) {
        return attendanceRepository.save(
                attendanceMapper.updateModelFromDTO(dto, id)
        );
    }

    public void deleteAttendance(Long id) {
        Attendance attendance = getAttendanceById(id);
        attendanceRepository.delete(attendance);
    }
}
