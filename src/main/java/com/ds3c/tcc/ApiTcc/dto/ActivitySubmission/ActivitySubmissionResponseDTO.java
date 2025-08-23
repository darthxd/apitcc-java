package com.ds3c.tcc.ApiTcc.dto.ActivitySubmission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySubmissionResponseDTO {
    private Long id;
    private Long activityId;
    private Long studentId;
    private String answerText;
    private String fileUrl;
    private String submissionDate;
    private Double grade;
}
