package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.OrderDTO;
import com.Logan.Java.Order.Manager.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO dto);

    List<OrderDTO> toDTOList(List<Order> orders);
    List<Order> toEntityList(List<OrderDTO> dtos);
}

