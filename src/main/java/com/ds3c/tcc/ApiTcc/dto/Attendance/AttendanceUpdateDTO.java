package com.ds3c.tcc.ApiTcc.dto.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUpdateDTO {
    private Long attendanceId;
    private Boolean present;
}
