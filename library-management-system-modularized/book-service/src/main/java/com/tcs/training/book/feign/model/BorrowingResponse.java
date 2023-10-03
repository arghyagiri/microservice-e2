package com.tcs.training.book.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowingResponse {

    private Long borrowingId;

    private Long bookId;

    private LocalDate borrowingDate;

    private LocalDate returnDate;

    private LocalDate dateReturned;

    private String userId;

    private int returned;

    private int eligibleForFineCollection;

}
