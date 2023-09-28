package com.tcs.training.ms.entity;

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
@Table(
        name = "BOOK_GENRE",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_BOOK_GENRE",
                        columnNames = {"bookId", "genreId"})
        })
public class BookGenre implements Serializable {

    @EmbeddedId
    BookGenreId id;

    @Embeddable
    public class BookGenreId implements Serializable {
        Long bookId;
        Long genreId;
    }
}
