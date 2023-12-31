package com.tcs.training.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVENTORY", uniqueConstraints = {
		@UniqueConstraint(name = "UC_INVENTORY_WAREHOUSE", columnNames = { "productId" }) })
public class Inventory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inv_sequence")
	@SequenceGenerator(name = "inv_sequence", allocationSize = 100)
	Long inventoryId;

	Long quantityAvailable;

	Long minimumStockLevel;

	Long maximumStockLevel;

	Long reorderPoint;

	Long productId;

	@JoinColumn(name = "productId", referencedColumnName = "productId")
	Product product;

}
