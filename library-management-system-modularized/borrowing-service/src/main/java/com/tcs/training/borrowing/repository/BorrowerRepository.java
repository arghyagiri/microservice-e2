package com.tcs.training.borrowing.repository;

import com.tcs.training.borrowing.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrowing, Long> {

	List<Borrowing> findByUserId(String userId);

	List<Borrowing> findByBookIdIn(List<Long> bookIds);

	List<Borrowing> findByUserIdAndReturned(String userId, int returned);

}
