package com.tcs.training.audit.repository;

import com.tcs.training.audit.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
