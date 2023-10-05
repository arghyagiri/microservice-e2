package com.tcs.training.customer.model.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

	@NotNull
	@Size(min = 1, max = 100)
	String firstName;

	@NotNull
	@Size(min = 1, max = 100)
	String lastName;

	@NotNull
	@Size(min = 1, max = 100)
	@Email
	String emailAddress;

}
