package com.tcs.training.order.service;

import com.tcs.training.order.entity.Customer;
import com.tcs.training.order.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	public Customer getById(Long id) {
		return customerRepository.findById(id).orElseThrow();
	}

	public List<Customer> getByIds(Set<Long> ids) {
		return customerRepository.findAllById(ids);
	}

	@Transactional
	public Customer add(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@Transactional
	public Customer put(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
	}

}
