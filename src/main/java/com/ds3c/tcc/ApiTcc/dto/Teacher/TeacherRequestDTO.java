package com.ds3c.tcc.ApiTcc.dto.Teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDTO {
    private String username;
    private String password;
    @NotBlank
    private String name;
    private String cpf;
    @Email
    private String email;
    private String phone;
    private Set<Long> subjectIds;
    private Set<Long> schoolClassIds;
    private Long unitId;
}
