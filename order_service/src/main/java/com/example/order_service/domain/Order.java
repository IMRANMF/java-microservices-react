package com.example.order_service.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String productCode;
    private String productName;
    private double price;
    private int quantity;
    private String status;

    public Order() {}

    public Order(String productCode, String productName, double price, int quantity, String status) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
