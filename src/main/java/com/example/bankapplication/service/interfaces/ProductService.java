package com.example.bankapplication.service.interfaces;

import com.example.bankapplication.dto.ProductDto;
import com.example.bankapplication.dto.ProductWithManagerAndQuantityDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto getProductById(UUID id);

    List<ProductDto> getAllProducts();

    List<ProductWithManagerAndQuantityDto> findAllProductsWhereAgreementsQuantityMoreThan(int quantity);

    List<ProductWithManagerAndQuantityDto> getProductsWithQuantityOfUsing();

}
