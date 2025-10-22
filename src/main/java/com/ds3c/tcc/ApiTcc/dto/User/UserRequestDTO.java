package com.ds3c.tcc.ApiTcc.dto.User;

public interface UserRequestDTO {
    String getUsername();
    String getPassword();
    Long getUnitId();
    void setUsername(String username);
    void setPassword(String password);
    void setUnitId(Long unitId);
}
