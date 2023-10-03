package com.tcs.training.borrowing.controller;

import com.tcs.training.borrowing.entity.Borrowing;
import com.tcs.training.borrowing.feign.model.BookDTO;
import com.tcs.training.borrowing.model.BorrowingRequest;
import com.tcs.training.borrowing.model.BorrowingResponse;
import com.tcs.training.borrowing.service.BorrowingService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping
    public List<Borrowing> getAll() {
        return borrowingService.getAll();
    }

    @PostMapping()
    @Transactional
    public BorrowingResponse add(@RequestBody Borrowing borrowing) {
        return borrowingService.add(borrowing);
    }

    @PostMapping("/init")
    @Transactional
    public List<Borrowing> borrowBooks(@RequestBody @Valid BorrowingRequest borrowing) {
        return borrowingService.borrowBooks(borrowing);
    }

    @PostMapping("/return")
    @Transactional
    public List<Borrowing> returnBooks(@RequestBody @Valid BorrowingRequest borrowing) {
        return borrowingService.returnBooks(borrowing);
    }

    @PutMapping()
    public Borrowing put(@RequestBody Borrowing borrowing) {
        return borrowingService.put(borrowing);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        borrowingService.delete(id);
    }

    @GetMapping(value = "/find-by-user/{userId}")
    public List<BorrowingResponse> getBorrowedBooksByUserId(@PathVariable("userId") String userId) {
        return borrowingService.getBorrowedBooksByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public Borrowing getBorrowedBooksById(@PathVariable("id") Long id) {
        return borrowingService.getBorrowedBooksByRecordId(id);
    }

    @GetMapping(value = "/over-due-books")
    public Set<BookDTO> getOverDueBooks() {
        return borrowingService.getOverDueBooks();
    }

}
