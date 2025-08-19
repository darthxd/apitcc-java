package com.ds3c.tcc.ApiTcc.dto.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentPresenceDTO {
    private Long studentId;
    private Boolean present;
}
