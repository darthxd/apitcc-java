package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.exception.UserNotFoundException;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CRUDService<User, Long> implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    public UserService(UserRepository userRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public boolean existsByRole(RolesEnum role) {
        return userRepository.existsByRole(role);
    }

    public String findDisplayNameById(Long userId) {
        User user = findById(userId);

        if (user instanceof Admin a) return a.getName();
        if (user instanceof Student s) return s.getName();
        if (user instanceof Teacher t) return t.getName();

        return user.getUsername();
    }
}
