package com.ds3c.tcc.ApiTcc.dto.SchoolUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUnitRequestDTO {
    private String name;
    private String address;
    private String phone;
    private String email;
}
