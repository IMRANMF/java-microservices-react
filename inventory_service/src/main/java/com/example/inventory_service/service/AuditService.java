package com.example.inventory_service.service;

import com.example.inventory_service.domain.Product;
import com.example.inventory_service.messaging.dto.InventoryStatusEvent;
import com.example.inventory_service.messaging.dto.OrderEvent;
import com.example.inventory_service.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;

    public AuditService(ProductRepository productRepository, RabbitTemplate rabbitTemplate) {
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async("inventoryTaskExecutor")
    public void processOrder(OrderEvent event) {
        Product product = productRepository.findByProductCode(event.productCode());

        if (product != null && product.getStock() >= event.quantity()) {
            product.setStock(product.getStock() - event.quantity());
            productRepository.save(product);
            System.out.println("⚡ [Inventory DB Update] Deducted stock. Remaining: " + product.getStock());
            rabbitTemplate.convertAndSend("inventory-response-exchange", "order.status.success",
                new InventoryStatusEvent(event.orderId(), "CONFIRMED"));
        } else {
            System.out.println("❌ [Inventory Service] Out of stock error!");
            rabbitTemplate.convertAndSend("inventory-response-exchange", "order.status.failure",
                new InventoryStatusEvent(event.orderId(), "FAILED"));
        }
    }

    @Async("inventoryTaskExecutor")
    public void restoreStock(OrderEvent event) {
        Product product = productRepository.findByProductCode(event.productCode());
        if (product != null) {
            product.setStock(product.getStock() + event.quantity());
            productRepository.save(product);
            System.out.println("↩️ [Inventory DB Update] Restored stock for " + event.productCode() + ". New total: " + product.getStock());
        }
    }
}
