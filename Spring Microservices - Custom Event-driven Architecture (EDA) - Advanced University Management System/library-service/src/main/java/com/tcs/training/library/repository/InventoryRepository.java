package com.tcs.training.library.repository;

import com.tcs.training.library.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Inventory findByProductIdAndPostCode(Long productId, String postCode);

}
