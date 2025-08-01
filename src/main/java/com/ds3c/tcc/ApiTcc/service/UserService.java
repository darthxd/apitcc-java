package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Admin.AdminCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.User.UserDTO;
import com.ds3c.tcc.ApiTcc.exception.UserNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.UserMapper;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDTO getUserById(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id)));
    }

    public User createUserByAdmin(AdminCreateDTO adminCreateDTO) {
        return userRepository.save(userMapper.fromAdminToModel(adminCreateDTO));
    }

    public List<UserDTO> listUser() {
        return userMapper.toListDTO(userRepository.findAll());
    }
}
