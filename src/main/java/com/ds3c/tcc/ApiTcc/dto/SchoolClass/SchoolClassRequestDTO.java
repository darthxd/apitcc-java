package com.ds3c.tcc.ApiTcc.dto.SchoolClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassRequestDTO {
    private String grade;
    private String course;
    private String shift;
    private Integer studentsLimit;
}
