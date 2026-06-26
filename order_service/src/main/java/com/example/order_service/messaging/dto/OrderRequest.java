package com.example.order_service.messaging.dto;

public record OrderRequest(String productCode, String productName, double price, int quantity) {}
