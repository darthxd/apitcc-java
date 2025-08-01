package com.ds3c.tcc.ApiTcc.dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    private String role;
}
