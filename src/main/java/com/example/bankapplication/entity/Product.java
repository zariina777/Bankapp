package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "product_limit")
    private BigDecimal product_limit; // 0 - no limit, 0 < - limit which can be used

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(status, product.status) && Objects.equals(currencyCode, product.currencyCode) && Objects.equals(interestRate, product.interestRate) && Objects.equals(product_limit, product.product_limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, currencyCode, interestRate, product_limit);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", manager=" + manager +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", currencyCode='" + currencyCode + '\'' +
                ", interestRate=" + interestRate +
                ", product_limit=" + product_limit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
