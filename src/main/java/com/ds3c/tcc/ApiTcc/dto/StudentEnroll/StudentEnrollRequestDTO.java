package com.ds3c.tcc.ApiTcc.dto.StudentEnroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollRequestDTO {
    private String name;
    private String ra;
    private String cpf;
    private String phone;
    private String email;
    private String gradeYear;
    private String course;
    private String shift;
    private String birthdate;
    private String address;
    private String photoUrl;
    private Long unitId;
    private LocalDate createdAt = LocalDate.now();
}
