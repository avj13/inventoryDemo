package com.example.licious.inventoryDemo.controller;

import com.example.licious.inventoryDemo.model.transactionEnum;
import com.example.licious.inventoryDemo.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Add to Inventory via Restocking.")
    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody AddInventoryRequest request){
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.addInventory(request.getProductId(), request.getQuantity(), transactionEnum.RESTOCK.toString());
            logger.info("Product: " + request.getProductId() + " with quantity:" + request.getQuantity() + "added to inventory.");
            return ResponseEntity.ok("Inventory added successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error at {}_{}: {}", "InventoryController", "addInventory", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add to Inventory via Item Return.")
    @PostMapping("/return")
    public ResponseEntity<String> addReturnedItem(@RequestBody AddInventoryRequest request){
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.addInventory(request.getProductId(), request.getQuantity(), transactionEnum.RETURN.toString());
            logger.info("Returnable Product: " + request.getProductId() + " with quantity:" + request.getQuantity() + "added to inventory.");

            //Initiate Refund process to Transaction/Payment Service.
            logger.info("Refund processed for Product: " + request.getProductId() + " with quantity:" + request.getQuantity());

            return ResponseEntity.ok("Product Returned successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error at {}_{}: {}", "InventoryController","addReturnedItem", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Remove from Inventory via valid Order.")
    @PostMapping("/fulfill")
    public ResponseEntity<String> fulfillInventory(@RequestBody FulfillInventoryRequest request) {
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.fulfillInventory(request.getProductId(), request.getQuantity());
            logger.info("Product: " + request.getProductId() + " with quantity:" + request.getQuantity() + "removed from inventory.");
            return ResponseEntity.ok("Inventory fulfilled successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error at {}_{}: {}", "InventoryController", "fulfillInventory",e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add to Inventory via Restocking.")
    @PostMapping("/get")
    public ResponseEntity<String> getAllInventory(){
        try {
            ResponseEntity reponse = inventoryService.getAllInventory();
            logger.info("Publish All Available Inventory.");
            return ResponseEntity.ok("");
        } catch (IllegalArgumentException e) {
            logger.error("Error at {}_{}: {}", "InventoryController", "addInventory", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Data
    static class AddInventoryRequest {
        private int productId;
        private int quantity;
    }

    @Data
    static class FulfillInventoryRequest {
        private int productId;
        private int quantity;
    }
}
