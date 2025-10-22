package com.ds3c.tcc.ApiTcc.dto.Student;

import com.ds3c.tcc.ApiTcc.dto.User.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO implements UserRequestDTO {
    private String username;
    private String password;
    private String name;
    private String ra;
    private String rm;
    private String cpf;
    private String phone;
    private String email;
    private Long schoolClassId;
    private String birthdate;
    private String photo;
    private Boolean sendNotification;
    private Long unitId;

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Long getUnitId() {
        return this.unitId;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}
