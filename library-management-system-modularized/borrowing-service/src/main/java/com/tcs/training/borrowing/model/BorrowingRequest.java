package com.tcs.training.borrowing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRequest {

    private Long bookId;

    private LocalDate borrowingDate;

    private LocalDate returnDate;

    private String userId;

}
