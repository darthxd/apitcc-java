package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.User.UserRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.exception.UserNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.UserMapper;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CRUDService<User, Long> implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SchoolUnitService schoolUnitService;

    @Autowired
    @Lazy
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder, SchoolUnitService schoolUnitService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.schoolUnitService = schoolUnitService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public boolean existsByRole(RolesEnum role) {
        return userRepository.existsByRole(role);
    }

    public User create(UserRequestDTO userRequestDTO, RolesEnum role, Long unitId) {
        User user = userMapper.toEntity(userRequestDTO, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSchoolUnit(schoolUnitService.findById(unitId));
        return save(user);
    }

    public User update(UserRequestDTO userRequestDTO, Long id) {
        return save(userMapper.updateEntityFromDTO(userRequestDTO, id));
    }
}
