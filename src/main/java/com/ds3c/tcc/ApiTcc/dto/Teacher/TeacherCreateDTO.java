package com.ds3c.tcc.ApiTcc.dto.Teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCreateDTO {
    @NotBlank
    private String name;
    private String cpf;
    @Email
    private String email;
    private String phone;
    private List<Long> classesIds;
    private List<Long> subjectsIds;
}
