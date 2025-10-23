package com.ds3c.tcc.ApiTcc.config;

import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserService userService;

    @Override
    public void run(String... args) {
        boolean hasAdmin = userService.existsByRole(RolesEnum.ROLE_ADMIN);
        if (!hasAdmin) {
            User admin = new User();

            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole(RolesEnum.ROLE_ADMIN);
            admin.setSchoolUnit(null);

            userService.save(admin);

            System.out.println("The Admin user has been created.");
            System.out.println("Username: admin / Password: admin123;");
        }
    }
}
