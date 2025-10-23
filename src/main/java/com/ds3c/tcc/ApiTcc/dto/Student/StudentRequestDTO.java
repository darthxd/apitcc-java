package com.ds3c.tcc.ApiTcc.dto.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
    private String username;
    private String password;
    private String name;
    private String ra;
    private String rm;
    private String cpf;
    private String phone;
    private String email;
    private Long schoolClassId;
    private String birthdate;
    private String photo;
    private Boolean sendNotification;
    private Long unitId;
}