package com.tcs.training.product.service;

import com.tcs.training.product.entity.Product;
import com.tcs.training.product.exception.NoDataFoundException;
import com.tcs.training.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<Product> getAll() {
		return productRepository.findAll();
	}

	public Product getById(Long id) {
		return productRepository.findById(id).orElseThrow();
	}

	public List<Product> getByIds(Set<Long> ids) {
		List<Product> products = productRepository.findAllById(ids);
		if(ids.size() != products.size() || products.stream().filter(p -> p.getQuantity() <= 0).toList().size() > 0){
			throw new NoDataFoundException("Requested products not available.");
		}
		return  products;
	}

	@Transactional
	public Product add(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@Transactional
	public Product put(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		productRepository.deleteById(id);
	}


}
