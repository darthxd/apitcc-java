package com.ds3c.tcc.ApiTcc.dto.Grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDTO {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private Long teacherId;
    private Integer bimester;
    private Integer year;
    private Integer grade;
    private String comment;
    private String createdAt;
}
