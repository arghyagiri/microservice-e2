package com.tcs.training.book.model;

import com.tcs.training.book.feign.model.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse extends BookDTO {

    private Set<AuthorDTO> authors;

}
