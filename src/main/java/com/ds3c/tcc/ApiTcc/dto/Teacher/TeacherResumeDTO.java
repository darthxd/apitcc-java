package com.ds3c.tcc.ApiTcc.dto.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResumeDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
}
