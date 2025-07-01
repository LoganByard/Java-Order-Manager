package com.Logan.Java.Order.Manager.security;

import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Role;
import com.Logan.Java.Order.Manager.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                Employee admin = new Employee();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setRole(Role.ADMIN);
                repository.save(admin);

                Employee manager = new Employee();
                manager.setUsername("manager");
                manager.setPassword(encoder.encode("manager123"));
                manager.setFirstName("Manager");
                manager.setLastName("User");
                manager.setRole(Role.MANAGER);
                repository.save(manager);

                Employee employee = new Employee();
                employee.setUsername("employee");
                employee.setPassword(encoder.encode("emp123"));
                employee.setFirstName("Employee");
                employee.setLastName("User");
                employee.setRole(Role.EMPLOYEE);
                repository.save(employee);

                System.out.println("Initial users setup");
            }
        };
    }
}
