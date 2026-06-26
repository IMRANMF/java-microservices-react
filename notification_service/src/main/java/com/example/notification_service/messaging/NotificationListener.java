package com.example.notification_service.messaging;

import com.example.notification_service.messaging.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @RabbitListener(queuesToDeclare = @Queue(name = "notification-queue", durable = "true"))
    public void dispatchNotification(OrderEvent event) {
        System.out.println("✉️ [Notification Service] Event consumed. Sending receipt email for Order ID: " + event.orderId());
    }
}
