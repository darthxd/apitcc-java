package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceMapper attendanceMapper;
    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceMapper attendanceMapper,
            AttendanceService attendanceService) {
        this.attendanceMapper = attendanceMapper;
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<AttendanceResponseDTO> listAttendance() {
        return attendanceService.listAttendance()
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @PostMapping
    public List<AttendanceResponseDTO> createAttendanceBulk(
            @RequestBody @Valid AttendanceBulkRequestDTO dto) {
        return attendanceService.createAttendanceBulk(dto)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO getAttendanceById(@PathVariable("id") Long id) {
        return attendanceMapper.toDTO(
                attendanceService.getAttendanceById(id)
        );
    }

    @GetMapping("/class/{classId}")
    public List<AttendanceResponseDTO> listAttendanceBySchoolClassId(@PathVariable("classId") Long classId) {
        return attendanceService.listAttendanceBySchoolClassId(classId)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/student/{id}")
    public List<AttendanceResponseDTO> listAttendancesByStudentId(@PathVariable("id") Long studentId) {
        return attendanceService.listAttendanceByStudentId(studentId)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/{classId}/{date}")
    public List<AttendanceResponseDTO> listAttendanceByDateAndClassId(
            @PathVariable("classId") Long classId,
            @PathVariable("date") String date
    ) {
        return attendanceService.listAttendanceByDateAndClassId(date, classId)
                .stream().map(attendanceMapper::toDTO).toList();
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
