package com.example.licious.inventoryDemo.service;

import com.example.licious.inventoryDemo.model.Inventory;
import com.example.licious.inventoryDemo.model.InventoryTransactions;
import com.example.licious.inventoryDemo.model.transactionEnum;
import com.example.licious.inventoryDemo.repository.InventoryRepository;
import com.example.licious.inventoryDemo.repository.InventoryTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryTransactionRepository = inventoryTransactionRepository;
    }

    @Override
    public void fulfillInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if(inventory.getCurrentQuantity() < quantity){
            throw new IllegalArgumentException("Insufficient Inventory for the product:" + productId);
        }
        inventory.setCurrentQuantity(inventory.getCurrentQuantity() - quantity);
        inventoryRepository.save(inventory);

        InventoryTransactions transaction = new InventoryTransactions();
        transaction.setProductId(productId);
        transaction.setTransactionType(transactionEnum.FULFILLMENT);
        transaction.setQuantity(quantity);
        inventoryTransactionRepository.save(transaction);

    }

    @Override
    public void addInventory(String productId, int quantity, String transactionType){
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if(inventory.getCurrentQuantity() + quantity > inventory.getMaxQuantity()){
            throw new IllegalArgumentException("Inventory addition exceeds maximum quantity for product: " + productId);
        }

        inventory.setCurrentQuantity(inventory.getCurrentQuantity() + quantity);
        inventoryRepository.save(inventory);

        InventoryTransactions transaction = new InventoryTransactions();
        transaction.setProductId(productId);
        transaction.setTransactionType(transactionEnum.RESTOCK);
        if(!transactionType.isEmpty())
            transaction.setTransactionType(transactionEnum.valueOf(transactionType));
        transaction.setQuantity(quantity);
        inventoryTransactionRepository.save(transaction);
    }
    
}
