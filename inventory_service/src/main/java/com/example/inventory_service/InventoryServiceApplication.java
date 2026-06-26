package com.example.inventory_service;

import com.example.inventory_service.domain.Product;
import com.example.inventory_service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(ProductRepository repository) {
        return args -> {
            if (repository.findByProductCode("MACBOOK-PRO") == null) {
                repository.save(new Product("MACBOOK-PRO", "MacBook Pro", 1299.99, 10));
                System.out.println("🌱 Seeded initial database stock: 10 MacBooks");
            }
        };
    }
}
