package com.ds3c.tcc.ApiTcc.dto.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {
    private String date;
    private Long studentId;
    private Long schoolClassId;
    private Long teacherId;
    private Boolean isInSchool;
    private Boolean present;
}
