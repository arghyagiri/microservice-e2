package com.tcs.training.book.controller;

import com.tcs.training.book.entity.Book;
import com.tcs.training.book.feign.model.BorrowingRequest;
import com.tcs.training.book.model.BookRequest;
import com.tcs.training.book.model.BookResponse;
import com.tcs.training.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping(value = "/{id}")
	public BookResponse getBookById(@PathVariable("id") Long id) {
		return bookService.getBookById(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<Book> getByIds(@RequestParam("id") Set<Long> ids) {
		return bookService.getByIds(ids);
	}

	@PostMapping()
	public Book createBook(@RequestBody BookRequest bookRequest) {
		return bookService.createBook(bookRequest);
	}

	@PutMapping()
	public Book updateBook(@RequestBody Book book) {
		return bookService.updateBook(book);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteBook(@PathVariable("id") Long id) {
		bookService.deleteBook(id);
	}

	@PostMapping("/update-status")
	public Book updateBookStatus(@RequestBody Book book) {
		return bookService.updateBookStatus(book);
	}

	@GetMapping("/find-by-author/{authorId}")
	public List<Book> getBooksByAuthorId(@PathVariable("authorId") Long authorId) {
		return bookService.getBooksByAuthorId(authorId);
	}

	@PostMapping(value = "/borrow")
	public List<Book> borrowBooks(@RequestBody BorrowingRequest borrowingRequest) {
		return bookService.borrowBooks(borrowingRequest);
	}

	@PostMapping(value = "/return")
	public List<Book> returnBooks(@RequestBody BorrowingRequest borrowingRequest) {
		return bookService.returnBooks(borrowingRequest);
	}

}
