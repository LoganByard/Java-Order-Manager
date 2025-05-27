package com.Logan.Java.Order.Manager.security;

import com.Logan.Java.Order.Manager.model.Employee;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


@Data
public class EmployeeUserDetails implements UserDetails {

    private final Employee employee;

    public EmployeeUserDetails(Employee employee) {

        this.employee = employee;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(employee.getRole().name()));

    }

    @Override
    public String getPassword() {

        return employee.getPassword();

    }

    @Override
    public String getUsername() {

        return employee.getUsername();

    }

    // potential isCredentialsNonExpired and isAccountNonLocked and isAccountNonExpired

}
