package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceBulkRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceMapper attendanceMapper;
    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceResponseDTO> findAll() {
        return attendanceService.findAll()
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @PostMapping
    public List<AttendanceResponseDTO> createBulk(
            @RequestBody @Valid AttendanceBulkRequestDTO dto) {
        return attendanceService.createBulk(dto)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO findById(@PathVariable("id") Long id) {
        return attendanceMapper.toDTO(
                attendanceService.findById(id)
        );
    }

    @GetMapping("/class/{classId}")
    public List<AttendanceResponseDTO> findAllBySchoolClass(@PathVariable("classId") Long classId) {
        return attendanceService.findAllBySchoolClass(classId)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/student/{id}")
    public List<AttendanceResponseDTO> findAllByStudent(@PathVariable("id") Long studentId) {
        return attendanceService.findAllByStudent(studentId)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @GetMapping("/{classId}/{date}")
    public List<AttendanceResponseDTO> findAllByDateAndSchoolClass(
            @PathVariable("classId") Long classId,
            @PathVariable("date") String date
    ) {
        return attendanceService.findAllByDateAndSchoolClass(date, classId)
                .stream().map(attendanceMapper::toDTO).toList();
    }

    @PutMapping("/{id}")
    public AttendanceResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttendanceRequestDTO dto
    ) {
        return attendanceMapper.toDTO(
                attendanceService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        attendanceService.delete(attendanceService.findById(id));
    }
}
