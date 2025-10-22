package com.ds3c.tcc.ApiTcc.dto.ActivitySubmission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCorrectionRequestDTO {
    private Double grade;
    private String comment;
    private Long teacherId;
}
