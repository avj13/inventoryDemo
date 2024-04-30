package com.example.licious.inventoryDemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Column(name = "product_id")
    private int productId;

    @Column(name = "current_quantity")
    private int currentQuantity;

    @Column(name = "max_quantity")
    private int maxQuantity;

}
