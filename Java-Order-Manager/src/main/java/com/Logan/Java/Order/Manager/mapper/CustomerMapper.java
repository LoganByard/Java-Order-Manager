package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.CustomerDTO;
import com.Logan.Java.Order.Manager.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDto(Customer customer);
    Customer toEntity(CustomerDTO dto);

    List<CustomerDTO> toDTOList(List<Customer> customers);
    List<Customer> toEntityList(List<CustomerDTO> dtos);
}

