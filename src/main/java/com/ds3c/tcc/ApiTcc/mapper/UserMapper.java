package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.User.UserRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserService userService;
    private final SchoolUnitService schoolUnitService;

    public User toEntity(UserRequestDTO userRequestDTO, RolesEnum role) {
        User user = new User();
        SchoolUnit schoolUnit = schoolUnitService.findById(userRequestDTO.getUnitId());

        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(role);
        user.setSchoolUnit(schoolUnit);

        return user;
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().name(),
                user.getSchoolUnit().getId(),
                userService.findDisplayNameById(user.getId())
        );
    }

    public User updateEntityFromDTO(UserRequestDTO userRequestDTO, User user) {
        if(StringUtils.hasText(userRequestDTO.getUsername())) {
            user.setUsername(userRequestDTO.getUsername());
        }
        if(StringUtils.hasText(userRequestDTO.getPassword())) {
            user.setPassword(userRequestDTO.getPassword());
        }
        if(userRequestDTO.getUnitId() != null) {
            user.setSchoolUnit(
                    schoolUnitService.findById(userRequestDTO.getUnitId())
            );
        }
        return user;
    }
}
