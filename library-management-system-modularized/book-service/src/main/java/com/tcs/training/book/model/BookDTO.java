package com.tcs.training.book.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    String isbn;

    String title;

    String edition;

    String category;

    LocalDate firstPublished;

    LocalDate lastEditionDate;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

}
