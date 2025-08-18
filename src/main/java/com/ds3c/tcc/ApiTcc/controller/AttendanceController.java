package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.model.Attendance;
import com.ds3c.tcc.ApiTcc.repository.AttendanceRepository;
import com.ds3c.tcc.ApiTcc.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public AttendanceResponseDTO createAttendance(@RequestBody @Valid AttendanceRequestDTO dto) {
        return attendanceMapper.toDTO(
                attendanceService.createAttendance(dto)
        );
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO getAttendanceById(@PathVariable("id") Long id) {
        return attendanceMapper.toDTO(
                attendanceService.getAttendanceById(id)
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
