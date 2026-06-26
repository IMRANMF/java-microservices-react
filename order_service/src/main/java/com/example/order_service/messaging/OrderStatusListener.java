package com.example.order_service.messaging;

import com.example.order_service.messaging.dto.InventoryStatusEvent;
import com.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusListener {
    private final OrderRepository orderRepository;

    public OrderStatusListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "order-update-queue")
    public void handleStatusUpdate(InventoryStatusEvent updateEvent) {
        orderRepository.findById(updateEvent.orderId()).ifPresent(order -> {
            order.setStatus(updateEvent.status());
            orderRepository.save(order);
            System.out.println("🔄 [Order DB Synced] Updated Order " + order.getId() + " status to: " + order.getStatus());
        });
    }
}
