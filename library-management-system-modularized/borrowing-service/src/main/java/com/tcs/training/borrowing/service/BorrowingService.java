package com.tcs.training.borrowing.service;

import com.tcs.training.borrowing.entity.Borrowing;
import com.tcs.training.borrowing.exception.NoDataFoundException;
import com.tcs.training.borrowing.feign.client.BookClient;
import com.tcs.training.borrowing.feign.model.BookDTO;
import com.tcs.training.borrowing.feign.model.BookStatus;
import com.tcs.training.borrowing.model.BorrowingRequest;
import com.tcs.training.borrowing.model.BorrowingResponse;
import com.tcs.training.borrowing.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowerRepository borrowerRepository;

    private final BookClient bookClient;

    @Value("${policy.return.days:10}")
    Long days;

    @Transactional
    public BorrowingResponse add(Borrowing borrowing) {
        BorrowingResponse borrowingResponse = new BorrowingResponse();
        Borrowing borrowingRecord = borrowerRepository.save(borrowing);
        BookDTO bookDTO = updateBookStatus(borrowing.getBookId(), BookStatus.BORROWED);
        BeanUtils.copyProperties(borrowingRecord, borrowingResponse);
        borrowingResponse.setBook(bookDTO);
        return borrowingResponse;
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
        List<Borrowing> borrowingRecords = borrowerRepository.findByUserIdAndReturned(userId, 0);
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

    @Transactional
    public List<Borrowing> borrowBooks(BorrowingRequest borrowing) {
        List<Borrowing> borrowings = new ArrayList<>();
        borrowing.getBookIds()
                .stream()
                .forEach(b -> borrowings.add(Borrowing.builder()
                        .borrowingDate(LocalDate.now())
                        .returnDate(LocalDate.now().plusDays(days))
                        .bookId(b)
                        .userId(borrowing.getUserId())
                        .returned(0)
                        .build()));
        return borrowerRepository.saveAll(borrowings);
    }

    public List<Borrowing> returnBooks(BorrowingRequest borrowing) {
        List<Borrowing> borrowings = borrowerRepository.findByBookIdIn(borrowing.getBookIds());
        borrowings.stream().forEach(b -> {
            b.setReturned(1);
            b.setDateReturned(LocalDate.now());
            if (b.getDateReturned().isAfter(b.getReturnDate())) {
                b.setEligibleForFineCollection(1);
            }
        });
        return borrowerRepository.saveAll(borrowings);
    }

    public Set<BookDTO> getOverDueBooks() {
        List<Borrowing> borrowingList = borrowerRepository.findOverDueBooks(LocalDate.now());
        return bookClient.getBooks(borrowingList.stream().map(Borrowing::getBookId).collect(Collectors.toSet()));
    }

}
