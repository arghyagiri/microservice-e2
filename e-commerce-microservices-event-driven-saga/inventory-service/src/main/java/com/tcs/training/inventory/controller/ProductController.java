package com.tcs.training.inventory.controller;

import com.tcs.training.inventory.entity.Product;
import com.tcs.training.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public List<Product> getAll() {
		return productService.getAll();
	}

	@GetMapping(value = "/{id}")
	public Product getById(@PathVariable("id") Long id) {
		return productService.getById(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<Product> getByIds(@RequestParam("id") Set<Long> ids) {
		return productService.getByIds(ids);
	}

	@PostMapping()
	public Product add(@RequestBody Product product) {
		Product productEntity = new Product();
		BeanUtils.copyProperties(product, productEntity);
		return productService.add(productEntity);
	}

	@PutMapping()
	public Product put(@RequestBody Product product) {
		return productService.put(product);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		productService.delete(id);
	}

}
