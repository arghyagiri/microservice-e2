package com.tcs.training.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK_AUTHOR",
        uniqueConstraints = {@UniqueConstraint(name = "UC_BOOK_AUTHOR", columnNames = {"bookId", "authorId"})})
public class BookAuthor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_author_sequence")
    @SequenceGenerator(name = "book_author_sequence", allocationSize = 100)
    Long id;

    Long bookId;

    Long authorId;

}
