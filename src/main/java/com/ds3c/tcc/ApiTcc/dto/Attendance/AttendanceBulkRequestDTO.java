package com.ds3c.tcc.ApiTcc.dto.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceBulkRequestDTO {
    private String date;
    private Long schoolClassId;
    private Long teacherId;
    private List<StudentPresenceDTO> presences;
}
