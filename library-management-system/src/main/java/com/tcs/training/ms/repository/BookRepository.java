package com.tcs.training.ms.repository;

import com.tcs.training.ms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b JOIN b.genres g WHERE g.genreId = :genreId")
	Set<Book> findByGenreId(@Param("genreId") Long id);

	@Query("SELECT b FROM Book b JOIN b.authors a WHERE a.authorId = :authorId")
	Set<Book> findByAuthorId(@Param("authorId") Long id);

	@Query("SELECT b FROM Book b JOIN FETCH b.libraryBranch l WHERE l.libraryBranchId = :libraryBranchId")
	List<Book> findByLibraryBranchId(@Param("libraryBranchId") Long id);

	@Query("SELECT b FROM Book b JOIN FETCH b.borrower br WHERE br.borrowerId = :borrowerId")
	List<Book> findByBorrowerId(@Param("borrowerId") Long id);

}
