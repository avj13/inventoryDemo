package com.example.licious.inventoryDemo.service;

public interface InventoryService {
    void fulfillInventory(String productId, int quantity);

    void addInventory(String productId, int quantity, String transactionType);
}
