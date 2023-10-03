package com.tcs.training.author.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String bio;

    private String country;

}
