package com.tcs.training.author.entity;

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
@Table(name = "AUTHOR",
		uniqueConstraints = { @UniqueConstraint(name = "UC_AUTHOR", columnNames = { "firstName", "lastName" }) })
public class Author implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
	@SequenceGenerator(name = "author_sequence", allocationSize = 100)
	Long authorId;

	String firstName;

	String lastName;

	String emailAddress;

	String bio;

	String country;

}
