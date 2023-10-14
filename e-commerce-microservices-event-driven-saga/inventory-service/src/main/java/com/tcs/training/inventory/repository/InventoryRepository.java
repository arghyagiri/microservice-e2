package com.tcs.training.inventory.repository;

import com.tcs.training.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Inventory findByProductId(Long productId);

}
