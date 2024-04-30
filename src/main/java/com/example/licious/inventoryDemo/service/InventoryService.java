package com.example.licious.inventoryDemo.service;

import org.springframework.http.ResponseEntity;

public interface InventoryService {
    void fulfillInventory(int productId, int quantity);

    void addInventory(int productId, int quantity, String transactionType);

    ResponseEntity getAllInventory();
}
