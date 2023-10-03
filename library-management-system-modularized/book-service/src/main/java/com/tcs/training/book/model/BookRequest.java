package com.tcs.training.book.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest extends BookDTO {

	private Set<Long> authorIds;

}
