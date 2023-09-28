package com.tcs.training.ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "BOOK",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_BOOK",
                        columnNames = {"isbn"})
        })
public class Book implements Serializable {
    @Id
    Long bookId;
    String isbn;
    String title;
    String edition;
    String category;
    LocalDate firstPublished;
    LocalDate lastEditionDate;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "genreId"))
    private Set<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "libraryBranchId")
    LibraryBranch libraryBranch;

    @ManyToOne
    @JoinColumn(name = "borrowerId")
    Borrower borrower;
}
