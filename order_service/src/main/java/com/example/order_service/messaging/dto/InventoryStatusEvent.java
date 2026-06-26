package com.example.order_service.messaging.dto;

public record InventoryStatusEvent(String orderId, String status) {}
