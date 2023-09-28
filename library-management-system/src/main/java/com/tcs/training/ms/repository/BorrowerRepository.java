package com.tcs.training.ms.repository;

import com.tcs.training.ms.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
