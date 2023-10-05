package com.tcs.training.user.entity;

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
@Table(name = "APP_USER",
		uniqueConstraints = { @UniqueConstraint(name = "UC_USER", columnNames = { "emailAddress" }) })
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@SequenceGenerator(name = "user_sequence", allocationSize = 100)
	Long userId;

	String firstName;

	String lastName;

	String emailAddress;

	String address;

	String contactNumber;

}
