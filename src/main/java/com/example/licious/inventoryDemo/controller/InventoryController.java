package com.example.licious.inventoryDemo.controller;

import com.example.licious.inventoryDemo.model.transactionEnum;
import com.example.licious.inventoryDemo.service.InventoryService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody AddInventoryRequest request){
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.addInventory(request.getProductId(), request.getQuantity(), "");
            return ResponseEntity.ok("Inventory added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> addReturnedItem(@RequestBody AddInventoryRequest request){
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.addInventory(request.getProductId(), request.getQuantity(), transactionEnum.RETURN.toString());

            //Initiate Refund process to Transaction/Payment Service.

            return ResponseEntity.ok("Product Returned successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/fulfill")
    public ResponseEntity<String> fulfillInventory(@RequestBody FulfillInventoryRequest request) {
        try {
            if(request.getQuantity() < 0)
                return ResponseEntity.badRequest().body("Invalid quantity.");

            inventoryService.fulfillInventory(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok("Inventory fulfilled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Data
    static class AddInventoryRequest {
        private String productId;
        private int quantity;
    }

    @Data
    static class FulfillInventoryRequest {
        private String productId;
        private int quantity;
    }
}
