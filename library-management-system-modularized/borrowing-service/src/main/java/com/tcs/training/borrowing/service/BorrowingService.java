package com.tcs.training.borrowing.service;

import com.tcs.training.borrowing.entity.Borrowing;
import com.tcs.training.borrowing.exception.NoDataFoundException;
import com.tcs.training.borrowing.feign.client.BookClient;
import com.tcs.training.borrowing.feign.model.BookDTO;
import com.tcs.training.borrowing.model.BookStatus;
import com.tcs.training.borrowing.model.BorrowingResponse;
import com.tcs.training.borrowing.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowerRepository borrowerRepository;

    private final BookClient bookClient;

    @Transactional
    public Borrowing add(Borrowing borrowing) {
        return borrowerRepository.save(borrowing);
    }

    @Transactional
    public Borrowing put(Borrowing borrowing) {
        return borrowerRepository.save(borrowing);
    }

    @Transactional
    public void delete(Long id) {
        borrowerRepository.deleteById(id);
    }

    public List<BorrowingResponse> getBorrowedBooksByUserId(String userId) {
        List<Borrowing> borrowingRecords = borrowerRepository.findByUserId(userId);
        if (borrowingRecords != null && !borrowingRecords.isEmpty()) {
            Set<BookDTO> borrowedBooks = bookClient
                    .getBooks(borrowingRecords.stream().map(Borrowing::getBookId).collect(Collectors.toSet()));
            List<BorrowingResponse> borrowingResponses = new ArrayList<>();
            borrowingRecords.forEach(v -> {
                BorrowingResponse borrowingResponse = new BorrowingResponse();
                BeanUtils.copyProperties(v, borrowingResponse);
                borrowingResponse
                        .setBooks(borrowedBooks.stream().filter(b -> b.getBookId().equals(v.getBookId())).toList());
                borrowingResponses.add(borrowingResponse);
            });
            return borrowingResponses;
        } else {
            throw new NoDataFoundException("No borrowing record found for user id : " + userId);
        }
    }

    @Transactional
    public BookDTO updateBookStatus(Long bookId, BookStatus bookStatus) {
        return bookClient.updateBookStatus(BookDTO.builder().bookId(bookId).status(bookStatus).build());
    }

    public List<Borrowing> getAll() {
        return borrowerRepository.findAll();
    }

    public Borrowing getBorrowedBooksByRecordId(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("No record found for id : " + id));
    }

}
