package com.tcs.training.inventory.controller;

import com.tcs.training.inventory.entity.Inventory;
import com.tcs.training.inventory.model.exception.InventoryDTO;
import com.tcs.training.inventory.model.exception.InventoryUpdateDTO;
import com.tcs.training.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping
	public List<Inventory> getAll() {
		return inventoryService.getAll();
	}

	@GetMapping(value = "/{id}")
	public Inventory getById(@PathVariable("id") Long id) {
		return inventoryService.getById(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<Inventory> getByIds(@RequestParam("id") Set<Long> ids) {
		return inventoryService.getByIds(ids);
	}

	@PostMapping()
	public Inventory add(@RequestBody @Valid InventoryDTO author) {
		Inventory inventoryEntity = new Inventory();
		BeanUtils.copyProperties(author, inventoryEntity);
		return inventoryService.add(inventoryEntity);
	}

	@PutMapping()
	public Inventory put(@RequestBody Inventory inventory) {
		return inventoryService.put(inventory);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		inventoryService.delete(id);
	}

	@PostMapping("/update")
	public InventoryUpdateDTO update(@RequestBody InventoryUpdateDTO inventoryDTO) {
		return inventoryService.update(inventoryDTO);
	}

}
