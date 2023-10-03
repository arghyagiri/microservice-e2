package com.tcs.training.borrowing.repository;

import com.tcs.training.borrowing.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByUserId(String userId);

    List<Borrowing> findByBookIdIn(List<Long> bookIds);

    List<Borrowing> findByUserIdAndReturned(String userId, int returned);

    @Query("select b from Borrowing b where b.returnDate < :today and b.returned = 0")
    List<Borrowing> findOverDueBooks(@Param("today") LocalDate today);

}
