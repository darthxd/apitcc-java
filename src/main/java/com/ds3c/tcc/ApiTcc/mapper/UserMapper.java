package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toModel(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        try {
            user.setRole(RolesEnum.valueOf(userCreateDTO.getRole()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    public List<UserDTO> toListDTO(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : userList) {
            userDTOList.add(toDTO(user));
        }
        return userDTOList;
    }

    public User fromAdminToModel(AdminCreateDTO adminCreateDTO) {
        User user = new User();
        user.setUsername(adminCreateDTO.getUsername());
        user.setPassword(adminCreateDTO.getPassword());
        user.setRole(RolesEnum.ROLE_ADMIN);
        return user;
    }
}
