package com.tcs.training.borrowing.controller;

import com.tcs.training.borrowing.entity.Borrowing;
import com.tcs.training.borrowing.feign.model.BookDTO;
import com.tcs.training.borrowing.model.BookStatus;
import com.tcs.training.borrowing.model.BorrowingResponse;
import com.tcs.training.borrowing.service.BorrowingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        BorrowingResponse borrowingResponse = new BorrowingResponse();
        Borrowing borrowingRecord = borrowingService.add(borrowing);
        BookDTO bookDTO = borrowingService.updateBookStatus(borrowing.getBookId(), BookStatus.BORROWED);
        BeanUtils.copyProperties(borrowingRecord, borrowingResponse);
        borrowingResponse.setBook(bookDTO);
        return borrowingResponse;
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

}
