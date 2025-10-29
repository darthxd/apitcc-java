package com.ds3c.tcc.ApiTcc.dto.Coordinator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatorResponseDTO {
    private Long id;
    private String username;
    private String password;
    private Long unitId;
    private String name;
    private String cpf;
    private String email;
    private String phone;
}
