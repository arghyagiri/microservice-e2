package com.tcs.training.book.repository;

import com.tcs.training.book.entity.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

    List<BookAuthor> findByBookId(Long bookId);

}
