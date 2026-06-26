package com.example.inventory_service.controller;

import com.example.inventory_service.domain.Product;
import com.example.inventory_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final ProductRepository productRepository;

    public InventoryController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(productRepository.findAll(PageRequest.of(page, Math.min(size, 100))));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (product.getProductCode() == null || product.getProductCode().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        Product p = productRepository.findByProductCode(productCode);
        if (p == null) return ResponseEntity.notFound().build();
        productRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}
