package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.PresenceLog.PresenceLogResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.PresenceLogMapper;
import com.ds3c.tcc.ApiTcc.service.PresenceLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presencelog")
public class PresenceLogController {
    private final PresenceLogMapper presenceLogMapper;
    private final PresenceLogService presenceLogService;

    public PresenceLogController(
            PresenceLogMapper presenceLogMapper,
            PresenceLogService presenceLogService) {
        this.presenceLogMapper = presenceLogMapper;
        this.presenceLogService = presenceLogService;
    }

    @GetMapping()
    public List<PresenceLogResponseDTO> list() {
        return presenceLogService.list().stream().map(presenceLogMapper::toDTO).toList();
    }

    @GetMapping("/student/{id}")
    public List<PresenceLogResponseDTO> listByStudent(
            @PathVariable("id") Long studentId) {
        return presenceLogService.listByStudent(studentId).stream().map(presenceLogMapper::toDTO).toList();
    }

    @GetMapping("/date/{date}")
    public List<PresenceLogResponseDTO> listByDate(
            @PathVariable("date") String date) {
        return presenceLogService.listByDate(date).stream().map(presenceLogMapper::toDTO).toList();
    }

    @GetMapping("/student/{id}/{date}")
    public PresenceLogResponseDTO getByStudentAndDate(
            @PathVariable("id") Long studentId,
            @PathVariable("date") String date) {
        return presenceLogMapper.toDTO(
                presenceLogService.getByStudentAndDate(studentId, date)
        );
    }
}
