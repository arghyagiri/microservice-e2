package com.tcs.training.payment.repository;

import com.tcs.training.payment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
