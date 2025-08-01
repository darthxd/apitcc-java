package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.User.UserResponseDTO;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> listUser() {
        return userService.listUser();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}
