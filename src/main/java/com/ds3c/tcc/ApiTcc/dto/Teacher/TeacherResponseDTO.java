package com.ds3c.tcc.ApiTcc.dto.Teacher;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectResumeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private List<SchoolClassResumeDTO> classes;
    private List<SchoolSubjectResumeDTO> subjects;
}
