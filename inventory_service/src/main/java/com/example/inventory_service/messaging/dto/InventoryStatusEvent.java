package com.example.inventory_service.messaging.dto;

public record InventoryStatusEvent(String orderId, String status) {}
