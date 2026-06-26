package com.example.inventory_service.messaging;

import com.example.inventory_service.messaging.dto.OrderEvent;
import com.example.inventory_service.service.AuditService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryListener {
    private final AuditService auditService;

    public InventoryListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "inventory-queue", durable = "true"))
    public void processStock(OrderEvent event) {
        auditService.processOrder(event);
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "return-queue", durable = "true"))
    public void processReturn(OrderEvent event) {
        auditService.restoreStock(event);
    }
}
