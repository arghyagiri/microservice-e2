package com.tcs.training.book.service;

import com.tcs.training.book.entity.Book;
import com.tcs.training.book.entity.BookAuthor;
import com.tcs.training.book.exception.NoDataFoundException;
import com.tcs.training.book.feign.client.AuthorClient;
import com.tcs.training.book.feign.client.BorrowClient;
import com.tcs.training.book.feign.model.AuthorDTO;
import com.tcs.training.book.feign.model.BorrowingRequest;
import com.tcs.training.book.feign.model.BorrowingResponse;
import com.tcs.training.book.model.BookRequest;
import com.tcs.training.book.model.BookResponse;
import com.tcs.training.book.model.BookStatus;
import com.tcs.training.book.repository.BookAuthorRepository;
import com.tcs.training.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookAuthorRepository bookAuthorRepository;

    private final AuthorClient authorClient;

    private final BorrowClient borrowClient;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookResponse getBookById(@PathVariable("id") Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        List<BookAuthor> bookAuthors = bookAuthorRepository.findByBookId(id);
        BookResponse bookResponse = new BookResponse();
        BeanUtils.copyProperties(book, bookResponse);
        if (!bookAuthors.isEmpty()) {
            bookResponse.setAuthors(authorClient
                    .getAuthors(bookAuthors.stream().map(BookAuthor::getAuthorId).collect(Collectors.toSet())));
        }
        return bookResponse;
    }

    public List<Book> getByIds(Set<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    @Transactional
    public Book createBook(@RequestBody BookRequest bookRequest) {
        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        bookRepository.save(book);
        Set<BookAuthor> bookAuthors = new HashSet<>();
        Set<AuthorDTO> availableAuthors = authorClient.getAuthors(book.getAuthorIds());
        availableAuthors.stream()
                .map(AuthorDTO::getAuthorId)
                .forEach(au -> bookAuthors.add(BookAuthor.builder().bookId(book.getBookId()).authorId(au).build()));
        if (!bookAuthors.isEmpty() && bookAuthors.size() == bookRequest.getAuthorIds().size()) {
            bookAuthorRepository.saveAll(bookAuthors);
        } else {
            Set<Long> missingAuthorIds = bookRequest.getAuthorIds();
            missingAuthorIds.removeAll(bookAuthors.stream().map(BookAuthor::getAuthorId).collect(Collectors.toSet()));
            throw new NoDataFoundException("[author-service] >> Invalid Author Ids : " + missingAuthorIds);
        }
        return book;
    }

    @Transactional
    public Book updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(@PathVariable("id") Long id) {
        bookAuthorRepository.deleteAll(bookAuthorRepository.findByBookId(id));
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book updateBookStatus(Book book) {
        Book availableBooks = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new NoDataFoundException("No book available with id : " + book.getBookId()));
        availableBooks.setStatus(book.getStatus());
        return bookRepository.save(availableBooks);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        List<BookAuthor> bookAuthors = bookAuthorRepository.findByAuthorId(authorId);
        return bookRepository.findAllById(bookAuthors.stream().map(BookAuthor::getBookId).toList());
    }

    public List<Book> borrowBooks(BorrowingRequest borrowingRequest) {
        List<Book> books = bookRepository.findAllById(borrowingRequest.getBookIds().stream().toList());
        if (borrowingRequest.getBookIds().size() == books.size()) {
            borrowClient.getBorrowingRecords(borrowingRequest);
            books.stream().forEach(b -> b.setStatus(BookStatus.BORROWED));
            return bookRepository.saveAll(books);
        } else {
            throw new NoDataFoundException("Requested Books do not exist.");
        }
    }

    public List<Book> returnBooks(BorrowingRequest borrowingRequest) {
        List<Book> books = bookRepository.findAllById(borrowingRequest.getBookIds().stream().toList());
        if (borrowingRequest.getBookIds().size() == books.size()) {
            List<BorrowingResponse> borrowingResponse = borrowClient.getReturnRecords(borrowingRequest);
            if (books.size() == borrowingResponse.size()) {
                books.stream().forEach(b -> b.setStatus(BookStatus.AVAILABLE));
                return bookRepository.saveAll(books);
            } else {
                throw new NoDataFoundException("Requested Books was not borrowed.");
            }
        } else {
            throw new NoDataFoundException("Requested Books do not exist.");
        }
    }

}
