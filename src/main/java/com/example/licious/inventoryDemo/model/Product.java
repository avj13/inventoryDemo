package com.example.licious.inventoryDemo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private int productId;

    private String name;

    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "supplier_id")
    private int supplierId;




}