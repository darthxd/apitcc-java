package com.ds3c.tcc.ApiTcc.dto.Student;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String ra;
    private String rm;
    private String cpf;
    private String phone;
    private String email;
    private SchoolClassResponseDTO schoolClass;
    private String birthdate;
    private String address;
    private String photo;
    private Boolean sendNotification;
    private Boolean biometry;
    private Boolean inschool;
    private Long unitId;
    private String status;
}
