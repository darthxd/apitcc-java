package com.ds3c.tcc.ApiTcc.dto.Grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequestDTO {
    private Long studentId;
    private Long subjectId;
    private Long teacherId;
    private Integer bimester;
    private Integer year;
    private Integer grade;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
