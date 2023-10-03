package com.tcs.training.book.entity;

import com.tcs.training.book.model.BookStatus;
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
@Table(name = "BOOK", uniqueConstraints = { @UniqueConstraint(name = "UC_BOOK", columnNames = { "isbn" }) })
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
	@SequenceGenerator(name = "book_sequence", allocationSize = 100)
	Long bookId;

	String isbn;

	String title;

	String edition;

	String category;

	LocalDate firstPublished;

	LocalDate lastEditionDate;

	@Transient
	private Set<Long> authorIds;

	@Enumerated(EnumType.STRING)
	private BookStatus status;

}
