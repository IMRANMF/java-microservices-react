package com.example.inventory_service.repository;

import com.example.inventory_service.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductCode(String productCode);
}
