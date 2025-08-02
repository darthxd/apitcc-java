package com.ds3c.tcc.ApiTcc.dto.SchoolClass;

import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.GradesEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassCreateDTO {
    private String name;
    private GradesEnum grade;
    private CoursesEnum course;
    private ShiftsEnum shift;
    private List<Long> teachersIds;
}
