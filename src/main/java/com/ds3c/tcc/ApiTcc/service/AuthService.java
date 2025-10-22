package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Auth.LoginRequestDTO;
import com.ds3c.tcc.ApiTcc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = (User) auth.getPrincipal();

        if (user.getSchoolUnit() == null) {
            throw new BadCredentialsException("User does not have an associated school unit.");
        }

        if (dto.getUnitId() == null ||
        !user.getSchoolUnit().getId().equals(dto.getUnitId())) {
            throw new BadCredentialsException("The user is not associated with the school unit you requested.");
        }

        return jwtService.generateToken((User) auth.getPrincipal(), dto.getUnitId());
    }
}
