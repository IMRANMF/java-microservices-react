package com.example.inventory_service.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String productCode;
    private String productName;
    private double price;
    private int stock;

    public Product() {}

    public Product(String productCode, String productName, double price, int stock) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
