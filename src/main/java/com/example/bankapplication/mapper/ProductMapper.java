package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ProductDto;
import com.example.bankapplication.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    List<ProductDto> toDtoList(List<Product> products);
}
