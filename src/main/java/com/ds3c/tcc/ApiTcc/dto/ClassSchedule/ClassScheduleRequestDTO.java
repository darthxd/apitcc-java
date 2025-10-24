package com.ds3c.tcc.ApiTcc.dto.ClassSchedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleRequestDTO {
    private Long schoolClassId;
    private Long teacherId;
    private Long subjectId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
