package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ProductDto;
import com.example.bankapplication.dto.ProductWithManagerAndQuantityDto;
import com.example.bankapplication.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product controller API", description = "With this controller you can get a list of all products," +
        " get product by ID, a list of products for which more than {quantity} contracts have been created " +
        "and a list of products with the amount of their use")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/find-all-products-where-agreement-quantity-more-than/{quantity}")
    @Operation(summary = "Get a list of products for which more than {quantity} contracts have been created")
    public ResponseEntity<List<ProductWithManagerAndQuantityDto>> findAllProductsWhereAgreementsQuantityMoreThan(@PathVariable("quantity") int quantity) {
        return ResponseEntity.ok(productService.findAllProductsWhereAgreementsQuantityMoreThan(quantity));
    }

    @GetMapping("/get-products-with-quantity-of-using")
    @Operation(summary = "Get a list of products with the amount of their use")
    public ResponseEntity<List<ProductWithManagerAndQuantityDto>> getProductsWithQuantityOfUsing() {
        return ResponseEntity.ok(productService.getProductsWithQuantityOfUsing());
    }

}
