package com.tcs.training.student.repository;

import com.tcs.training.student.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
