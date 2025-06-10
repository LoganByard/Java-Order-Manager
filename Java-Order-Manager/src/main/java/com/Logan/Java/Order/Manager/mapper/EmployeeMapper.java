package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.EmployeeDTO;
import com.Logan.Java.Order.Manager.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toDTO(Employee employee);
    Employee toEntity(EmployeeDTO dto);

    List<EmployeeDTO> toDTOList(List<Employee> employees);
    List<Employee> toEntityList(List<EmployeeDTO> dtos);
}

