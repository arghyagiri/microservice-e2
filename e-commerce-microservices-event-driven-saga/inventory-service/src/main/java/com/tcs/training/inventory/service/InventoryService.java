package com.tcs.training.inventory.service;

import com.tcs.training.inventory.entity.Inventory;
import com.tcs.training.inventory.exception.NoDataFoundException;
import com.tcs.training.inventory.model.exception.InventoryUpdateDTO;
import com.tcs.training.inventory.model.exception.ProductDTO;
import com.tcs.training.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}

	public Inventory getById(Long id) {
		return inventoryRepository.findById(id)
			.orElseThrow(() -> new NoDataFoundException("No User found with id : " + id));
	}

	public List<Inventory> getByIds(Set<Long> ids) {
		return inventoryRepository.findAllById(ids);
	}

	@Transactional
	public Inventory add(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Transactional
	public Inventory put(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		inventoryRepository.deleteById(id);
	}

	public InventoryUpdateDTO update(InventoryUpdateDTO inventoryDTO) {
		InventoryUpdateDTO result = InventoryUpdateDTO.builder()
			.postCode(inventoryDTO.getPostCode())
			.products(new HashSet<>())
			.build();
		for (ProductDTO productDTO : inventoryDTO.getProducts()) {
			Inventory existingInventory = inventoryRepository.findByProductIdAndPostCode(productDTO.getProductId(),
					inventoryDTO.getPostCode());
			existingInventory.setQuantityAvailable(existingInventory.getQuantityAvailable() - productDTO.getQuantity());
			inventoryRepository.save(existingInventory);
			productDTO.setAvailableQuantity(existingInventory.getQuantityAvailable());
			result.getProducts().add(productDTO);
		}
		return result;
	}

}
