package com.Logan.Java.Order.Manager.mapper;

import com.Logan.Java.Order.Manager.dto.ProductDTO;
import com.Logan.Java.Order.Manager.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDTOList(List<Product> products);
    List<Product> toEntityList(List<ProductDTO> dtos);

}
