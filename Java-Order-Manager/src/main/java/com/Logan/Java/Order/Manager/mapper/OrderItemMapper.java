package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.OrderItemDTO;
import com.Logan.Java.Order.Manager.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDTO toDTO(OrderItem orderItem);
    OrderItem toEntity(OrderItemDTO dto);

    List<OrderItemDTO> toDTOList(List<OrderItem> orderItems);
    List<OrderItem> toEntityList(List<OrderItemDTO> dtos);
}
