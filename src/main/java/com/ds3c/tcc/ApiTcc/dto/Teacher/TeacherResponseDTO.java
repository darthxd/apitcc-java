package com.ds3c.tcc.ApiTcc.dto.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private Set<Long> subjectIds;
    private Set<Long> schoolClassIds;
}
