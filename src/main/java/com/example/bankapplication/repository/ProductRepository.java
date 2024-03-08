package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ProductWithManagerAndQuantityDto;
import com.example.bankapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "SELECT new com.example.bankapplication.dto.ProductWithManagerAndQuantityDto(a.product.id, a.product.name, CONCAT(a.product.manager.firstName, ' ', a.product.manager.lastName), COUNT(a.product.id)) FROM Agreement a GROUP BY a.product.id, a.product.name, a.product.manager.firstName, a.product.manager.lastName HAVING COUNT(a.product.id) > :quantity")
    List<ProductWithManagerAndQuantityDto> findAllProductsWhereAgreementsQuantityMoreThan(int quantity);

    @Query(value = "SELECT new com.example.bankapplication.dto.ProductWithManagerAndQuantityDto(p.id, p.name, CONCAT(p.manager.firstName, ' ', p.manager.lastName), COALESCE(COUNT(a.product), 0)) FROM Product p LEFT JOIN Agreement a ON p = a.product GROUP BY p.id, p.name, p.manager.firstName, p.manager.lastName ORDER BY p.id")
    List<ProductWithManagerAndQuantityDto> getProductsWithQuantityOfUsing();

}
