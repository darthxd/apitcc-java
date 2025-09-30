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
    public List<PresenceLogResponseDTO> listPresenceLog() {
        return presenceLogMapper.toListDTO(
                presenceLogService.listPresenceLog()
        );
    }

    @GetMapping("/student/{id}")
    public List<PresenceLogResponseDTO> listPresenceLogByStudentId(
            @PathVariable("id") Long studentId) {
        return presenceLogMapper.toListDTO(
                presenceLogService.listPresenceLogByStudentId(studentId)
        );
    }

    @GetMapping("/date/{date}")
    public List<PresenceLogResponseDTO> listPresenceLogByDate(
            @PathVariable("date") String date) {
        return presenceLogMapper.toListDTO(
                presenceLogService.listPresenceLogByDate(date)
        );
    }

    @GetMapping("/student/{id}/{date}")
    public PresenceLogResponseDTO getPresenceLogByStudentIdAndDate(
            @PathVariable("id") Long studentId,
            @PathVariable("date") String date) {
        return presenceLogMapper.toDTO(
                presenceLogService.getPresenceLogByStudentIdAndDate(studentId, date)
        );
    }
}
