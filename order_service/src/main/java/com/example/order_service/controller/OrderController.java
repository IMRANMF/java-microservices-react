package com.example.order_service.controller;

import com.example.order_service.domain.Order;
import com.example.order_service.messaging.dto.OrderEvent;
import com.example.order_service.messaging.dto.OrderRequest;
import com.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderController(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        Order newOrder = new Order(request.productCode(), request.productName(), request.price(), request.quantity(), "PENDING");
        newOrder = orderRepository.save(newOrder);
        System.out.println("💾 [Order Service] Saved Order to Database with ID: " + newOrder.getId());

        OrderEvent event = new OrderEvent(newOrder.getId(), request.productCode(), request.quantity());
        rabbitTemplate.convertAndSend("order-exchange", "", event);

        return ResponseEntity.ok(new OrderResponse(newOrder.getId(), "PENDING"));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        return orderRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<OrderResponse> returnOrder(@PathVariable String id) {
        Optional<Order> found = orderRepository.findById(id);
        if (found.isEmpty()) return ResponseEntity.notFound().build();
        Order order = found.get();
        if (!"CONFIRMED".equals(order.getStatus())) return ResponseEntity.badRequest().build();
        order.setStatus("RETURNED");
        orderRepository.save(order);
        System.out.println("↩️ [Order Service] Order " + order.getId() + " marked as RETURNED");
        rabbitTemplate.convertAndSend("return-exchange", "",
            new OrderEvent(order.getId(), order.getProductCode(), order.getQuantity()));
        return ResponseEntity.ok(new OrderResponse(order.getId(), "RETURNED"));
    }

    record OrderResponse(String orderId, String status) {}
}
