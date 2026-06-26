package com.example.order_service.messaging.dto;

public record OrderEvent(String orderId, String productCode, int quantity) {}
