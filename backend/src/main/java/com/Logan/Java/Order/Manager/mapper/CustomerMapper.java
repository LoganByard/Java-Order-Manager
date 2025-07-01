package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.CustomerDTO;
import com.Logan.Java.Order.Manager.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    //CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "id", target = "id")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "id", target = "id")
    Customer toEntity(CustomerDTO dto);

    List<CustomerDTO> toDTOList(List<Customer> customers);
    List<Customer> toEntityList(List<CustomerDTO> dtos);
}

