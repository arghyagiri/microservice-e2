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
        name = "BOOK_AUTHOR",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_BOOK_AUTHOR",
                        columnNames = {"bookId", "authorId"})
        })
public class BookAuthor implements Serializable {

    @EmbeddedId
    BookAuthorId id;

    @Embeddable
    public class BookAuthorId implements Serializable {
        Long bookId;
        Long authorId;
    }
}


