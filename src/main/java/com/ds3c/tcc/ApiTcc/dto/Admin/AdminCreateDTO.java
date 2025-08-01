package com.ds3c.tcc.ApiTcc.dto.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateDTO {
    private Long id;
    private String username;
    private String password;
    @NotBlank
    private String name;
    @Email
    private String email;
    private String cpf;
    private String phone;
}
