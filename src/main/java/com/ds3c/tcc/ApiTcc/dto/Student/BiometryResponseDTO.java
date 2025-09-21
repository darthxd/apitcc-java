package com.ds3c.tcc.ApiTcc.dto.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiometryResponseDTO {
    private String status;
    private String confidence;
    private Long studentId;
}
