package com.tcs.training.book.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    private Long authorId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String bio;

    private String country;

}
