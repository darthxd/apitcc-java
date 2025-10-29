package com.ds3c.tcc.ApiTcc.dto.StudentEnroll;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollResponseDTO {
    private Long id;
    private String name;
    private String rm;
    private String ra;
    private String cpf;
    private String phone;
    private String email;
    private String gradeYear;
    private String course;
    private String shift;
    private Long schoolClassId;
    private String birthdate;
    private String address;
    private String photoUrl;
    private Long unitId;
    private String status;
    private String createdAt;
}
