package com.example.licious.inventoryDemo.service;

import com.example.licious.inventoryDemo.model.Inventory;
import com.example.licious.inventoryDemo.model.InventoryTransactions;
import com.example.licious.inventoryDemo.model.transactionEnum;
import com.example.licious.inventoryDemo.repository.InventoryRepository;
import com.example.licious.inventoryDemo.repository.InventoryTransactionRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.Counter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final Counter returnCounter;
    //track refund requests.
    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryServiceImpl(MeterRegistry meterRegistry, InventoryRepository inventoryRepository, InventoryTransactionRepository inventoryTransactionRepository) {
        this.returnCounter = (Counter) meterRegistry.counter("item_refund_counter");
        this.inventoryRepository = inventoryRepository;
        this.inventoryTransactionRepository = inventoryTransactionRepository;
    }

    @Override
    public void fulfillInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if(inventory.getCurrentQuantity() < quantity){
            logger.info("Insufficient Inventory for product: "+ productId + ". Quantity: available: "+ inventory.getCurrentQuantity() + " requested: " + quantity );
            throw new IllegalArgumentException("Insufficient Inventory for the product:" + productId);
        }
        inventory.setCurrentQuantity(inventory.getCurrentQuantity() - quantity);
        inventoryRepository.save(inventory);
        logger.info("Inventory Updated. Product: " + productId + " -" + quantity+".");

        InventoryTransactions transaction = InventoryTransactions.builder().productId(productId).transactionType(transactionEnum.FULFILLMENT).quantity(quantity).build();
        inventoryTransactionRepository.save(transaction);

    }

    @Override
    public void addInventory(String productId, int quantity, String transactionType){
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if(inventory.getCurrentQuantity() + quantity > inventory.getMaxQuantity()){
            logger.info("Maximum Inventory reached for product: "+ productId + ". Quantity: available: " + inventory.getCurrentQuantity() + " requested: " + quantity );
            throw new IllegalArgumentException("Inventory addition exceeds maximum quantity for product: " + productId);
        }

        if(Objects.equals(transactionType, transactionEnum.RETURN.toString()))
            returnCounter.inc();

        inventory.setCurrentQuantity(inventory.getCurrentQuantity() + quantity);
        inventoryRepository.save(inventory);
        logger.info("Inventory Updated. Type: " + transactionType + " Product: " + productId + " +" + quantity+".");

        InventoryTransactions transaction = InventoryTransactions.builder().productId(productId).transactionType(transactionEnum.valueOf(transactionType)).quantity(quantity).build();
        inventoryTransactionRepository.save(transaction);
    }
    
}
