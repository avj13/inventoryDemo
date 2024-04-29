package com.example.licious.inventoryDemo.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    private String name;

    private String category;

    private String subCategory;

    @ManyToOne
    @JoinColumn(name = "supplier_supplier_id")
    private Long supplierId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSupplier() {
        return supplierId;
    }

    public void setSupplier(Long supplierId) {
        this.supplierId = supplierId;
    }

}