package com.tcs.training.author.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

	Long bookId;

	String isbn;

	String title;

	String edition;

	String category;

	LocalDate firstPublished;

	LocalDate lastEditionDate;

}
