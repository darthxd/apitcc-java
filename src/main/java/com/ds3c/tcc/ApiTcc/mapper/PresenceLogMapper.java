package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.PresenceLog.PresenceLogResponseDTO;
import com.ds3c.tcc.ApiTcc.model.StudentPresenceLog;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PresenceLogMapper {
    public PresenceLogResponseDTO toDTO(StudentPresenceLog presenceLog) {
        PresenceLogResponseDTO dto = new PresenceLogResponseDTO();
        dto.setId(presenceLog.getId());
        dto.setStudentId(presenceLog.getStudent().getId());
        dto.setDate(presenceLog.getDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (presenceLog.getEntryTime() != null) {
            dto.setEntryTime(presenceLog.getEntryTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        if (presenceLog.getExitTime() != null) {
            dto.setExitTime(presenceLog.getExitTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        return dto;
    }
    public List<PresenceLogResponseDTO> toListDTO(List<StudentPresenceLog> presenceLogList) {
        List<PresenceLogResponseDTO> dtoList = new ArrayList<>();
        for (StudentPresenceLog presenceLog : presenceLogList) {
            dtoList.add(toDTO(presenceLog));
        }
        return dtoList;
    }
}
