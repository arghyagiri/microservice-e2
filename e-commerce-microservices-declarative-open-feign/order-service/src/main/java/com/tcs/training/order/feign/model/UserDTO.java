package com.tcs.training.order.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	Long userId;

	String firstName;

	String lastName;

	String emailAddress;

	List<OrderDTO> orders;

}
