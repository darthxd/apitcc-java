package com.ds3c.tcc.ApiTcc.config;

import com.ds3c.tcc.ApiTcc.enums.RolesEnum;
import com.ds3c.tcc.ApiTcc.model.Admin;
import com.ds3c.tcc.ApiTcc.model.SchoolUnit;
import com.ds3c.tcc.ApiTcc.service.AdminService;
import com.ds3c.tcc.ApiTcc.service.SchoolUnitService;
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
    private final SchoolUnitService schoolUnitService;

    @Override
    public void run(String... args) {
        boolean hasAdmin = userService.existsByRole(RolesEnum.ROLE_ADMIN);
        boolean hasSchoolUnit = !schoolUnitService.findAll().isEmpty();
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
        if (!hasSchoolUnit) {
            SchoolUnit schoolUnit = new SchoolUnit();

            schoolUnit.setName("ETEC Polivalente Americana");
            schoolUnit.setAddress("Rua Exemplo, 1000 - Centro");
            schoolUnit.setPhone("11999999999");
            schoolUnit.setEmail("polivalente@etec.com");

            schoolUnitService.save(schoolUnit);

            System.out.println("The School Unit "+schoolUnit.getName()+" has been created.");
        }
    }
}
