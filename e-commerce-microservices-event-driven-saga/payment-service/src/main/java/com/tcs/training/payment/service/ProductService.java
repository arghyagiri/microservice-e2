package com.tcs.training.payment.service;

import com.tcs.training.payment.entity.Product;
import com.tcs.training.payment.exception.NoDataFoundException;
import com.tcs.training.payment.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
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
		log.info("products :: {}", products);
		if (ids.size() != products.size()) {
			throw new NoDataFoundException("Requested products not available.");
		}
		return products;
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
