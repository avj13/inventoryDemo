package com.example.licious.inventoryDemo.repository;

import com.example.licious.inventoryDemo.model.InventoryTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransactions, Integer> {
}
