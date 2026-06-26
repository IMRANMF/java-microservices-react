package com.example.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange orderExchange() { return new FanoutExchange("order-exchange"); }

    @Bean
    public Queue inventoryQueue() { return new Queue("inventory-queue"); }

    @Bean
    public Queue notificationQueue() { return new Queue("notification-queue"); }

    @Bean
    public Binding bindInventory(Queue inventoryQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(inventoryQueue).to(orderExchange);
    }

    @Bean
    public Binding bindNotification(Queue notificationQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(notificationQueue).to(orderExchange);
    }

    @Bean
    public TopicExchange inventoryResponseExchange() {
        return new TopicExchange("inventory-response-exchange");
    }

    @Bean
    public Queue orderUpdateQueue() {
        return new Queue("order-update-queue");
    }

    @Bean
    public Binding bindOrderUpdate(Queue orderUpdateQueue, TopicExchange inventoryResponseExchange) {
        return BindingBuilder.bind(orderUpdateQueue).to(inventoryResponseExchange).with("order.status.*");
    }

    @Bean
    public FanoutExchange returnExchange() { return new FanoutExchange("return-exchange"); }

    @Bean
    public Queue returnQueue() { return new Queue("return-queue", true); }

    @Bean
    public Binding bindReturn(Queue returnQueue, FanoutExchange returnExchange) {
        return BindingBuilder.bind(returnQueue).to(returnExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
