package com.tcs.training.customer.controller;

import com.tcs.training.customer.entity.Customer;
import com.tcs.training.customer.model.exception.CustomerDTO;
import com.tcs.training.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public List<Customer> getAll() {
		return customerService.getAll();
	}

	@GetMapping(value = "/{id}")
	public Customer getById(@PathVariable("id") Long id) {
		return customerService.getById(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<Customer> getByIds(@RequestParam("id") Set<Long> ids) {
		return customerService.getByIds(ids);
	}

	@PostMapping()
	public Customer add(@RequestBody @Valid CustomerDTO author) {
		Customer customerEntity = new Customer();
		BeanUtils.copyProperties(author, customerEntity);
		return customerService.add(customerEntity);
	}

	@PutMapping()
	public Customer put(@RequestBody Customer customer) {
		return customerService.put(customer);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		customerService.delete(id);
	}


}
