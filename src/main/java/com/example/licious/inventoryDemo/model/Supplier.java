package com.example.licious.inventoryDemo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supplier_id", nullable = false)
    private int supplierId;

    @Column(name = "supplier_name")
    private String supplierName;
    private String contact;
    private String address;

}
