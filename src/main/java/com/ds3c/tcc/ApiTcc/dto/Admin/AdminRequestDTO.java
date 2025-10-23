package com.ds3c.tcc.ApiTcc.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Long unitId;
}
