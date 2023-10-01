package com.tcs.training.borrowing.repository;

import com.tcs.training.borrowing.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByUserId(String userId);

}
