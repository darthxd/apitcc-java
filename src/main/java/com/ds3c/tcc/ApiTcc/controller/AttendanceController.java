package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceMapper attendanceMapper;
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceMapper attendanceMapper,
                                AttendanceService attendanceService) {
        this.attendanceMapper = attendanceMapper;
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<AttendanceResponseDTO> listAttendance() {
        return attendanceMapper.toListDTO(attendanceService.listAttendance());
    }

    @PostMapping
    public List<AttendanceResponseDTO> createAttendanceBulk(
            @RequestBody @Valid AttendanceBulkRequestDTO dto) {
        return attendanceMapper.toListDTO(
                attendanceService.createAttendanceBulk(dto)
        );
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO getAttendanceById(@PathVariable("id") Long id) {
        return attendanceMapper.toDTO(
                attendanceService.getAttendanceById(id)
        );
    }

    @GetMapping("/class/{classId}")
    public List<AttendanceResponseDTO> listAttendanceBySchoolClassId(@PathVariable("classId") Long classId) {
        return attendanceMapper.toListDTO(
                attendanceService.listAttendanceBySchoolClassId(classId)
        );
    }

    @GetMapping("/student/{id}")
    public List<AttendanceResponseDTO> listAttendancesByStudentId(@PathVariable("id") Long studentId) {
        return attendanceMapper.toListDTO(
                attendanceService.listAttendanceByStudentId(studentId)
        );
    }

    @GetMapping("/{classId}/{date}")
    public List<AttendanceResponseDTO> listAttendanceByDateAndClassId(
            @PathVariable("classId") Long classId,
            @PathVariable("date") String date
    ) {
        return attendanceMapper.toListDTO(
                attendanceService.listAttendanceByDateAndClassId(date, classId)
        );
    }

    @PutMapping("/{id}")
    public AttendanceResponseDTO updateAttendance(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttendanceRequestDTO dto
    ) {
        return attendanceMapper.toDTO(
                attendanceService.updateAttendance(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
    }
}
