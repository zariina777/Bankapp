package com.example.bankapplication.service.implementations;

import com.example.bankapplication.dto.ProductDto;
import com.example.bankapplication.dto.ProductWithManagerAndQuantityDto;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.exceptions.EntityNotFoundException;
import com.example.bankapplication.mapper.ProductMapper;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductWithManagerAndQuantityDto> findAllProductsWhereAgreementsQuantityMoreThan(int quantity) {
        return productRepository.findAllProductsWhereAgreementsQuantityMoreThan(quantity);
    }

    @Override
    public List<ProductWithManagerAndQuantityDto> getProductsWithQuantityOfUsing() {
        return productRepository.getProductsWithQuantityOfUsing();
    }
}
