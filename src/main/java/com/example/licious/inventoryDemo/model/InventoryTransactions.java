package com.example.licious.inventoryDemo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name= "product_id")
    private int productId;

    @Column(name = "transaction_type")
    private transactionEnum transactionType;
    private int quantity;

    @Column(name = "created_at_timestamp", nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    private Timestamp timestamp;
    private String description;

}
