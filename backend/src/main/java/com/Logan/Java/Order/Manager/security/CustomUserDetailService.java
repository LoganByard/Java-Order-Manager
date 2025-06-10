package com.Logan.Java.Order.Manager.security;

import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new EmployeeUserDetails(employee);

    }
}
