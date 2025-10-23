package com.ds3c.tcc.ApiTcc.config;

import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;

    @Override
    public void run(String... args) {
        boolean hasAdmin = userService.existsByRole(RolesEnum.ROLE_ADMIN);
        if (!hasAdmin) {
            Admin admin = new Admin();

            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(RolesEnum.ROLE_ADMIN);
            admin.setSchoolUnit(null);

            adminService.save(admin);

            System.out.println("The Admin user has been created.");
            System.out.println("Username: admin / Password: admin123;");
        }
    }
}
