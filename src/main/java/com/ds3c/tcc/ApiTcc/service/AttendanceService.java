package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Attendance.AttendanceRequestDTO;
import com.ds3c.tcc.ApiTcc.exception.AttendanceNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.AttendanceMapper;
import com.ds3c.tcc.ApiTcc.model.Attendance;
import com.ds3c.tcc.ApiTcc.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Autowired
    @Lazy
    public AttendanceService(AttendanceRepository attendanceRepository,
                             AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(id));
    }

    public List<Attendance> listAttendances() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> listAttendancesByDateAndClassId(String date, Long classId) {
        return attendanceRepository.findAllByDateAndSchoolClassId(
                LocalDate.parse(date), classId
        );
    }

    public Attendance createAttendance(AttendanceRequestDTO dto) {
        return attendanceRepository.save(
                attendanceMapper.toModel(dto)
        );
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
