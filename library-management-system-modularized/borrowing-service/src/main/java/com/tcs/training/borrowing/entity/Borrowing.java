package com.tcs.training.borrowing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BORROWING",
        uniqueConstraints = {@UniqueConstraint(name = "UC_BORROWING", columnNames = {"bookId", "userId"})})
public class Borrowing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowing_sequence")
    @SequenceGenerator(name = "borrowing_sequence", allocationSize = 100)
    private Long borrowingId;

    private Long bookId;

    private LocalDate borrowingDate;

    private LocalDate returnDate;

    private LocalDate dateReturned;

    private String userId;

    private int returned;

    private int eligibleForFineCollection;

}
