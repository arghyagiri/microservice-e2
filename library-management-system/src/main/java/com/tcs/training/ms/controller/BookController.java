package com.tcs.training.ms.controller;

import com.tcs.training.ms.entity.Book;
import com.tcs.training.ms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BorrowerRepository borrowerRepository;
    private final GenreRepository genreRepository;
    private final LibraryBranchRepository libraryBranchRepository;

    @GetMapping
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Book getById(@PathVariable("id") Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PostMapping()
    public Book add(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping()
    public Book put(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
    }

    @GetMapping("/genre/{id}")
    public Set<Book> getBooksByGenreId(@PathVariable("id") Long id) {
        return bookRepository.findByGenreId(id);
    }

    @GetMapping("/author/{id}")
    public Set<Book> getBooksByAuthorId(@PathVariable("id") Long id) {
        return bookRepository.findByAuthorId(id);
    }

    @GetMapping("/library-branch/{id}")
    public List<Book> getBooksByLibraryBranchId(@PathVariable("id") Long id) {
        return bookRepository.findByLibraryBranchId(id);
    }

    @GetMapping("/borrower/{id}")
    public List<Book> getBooksByBorrowerId(@PathVariable("id") Long id) {
        return bookRepository.findByBorrowerId(id);
    }
}
