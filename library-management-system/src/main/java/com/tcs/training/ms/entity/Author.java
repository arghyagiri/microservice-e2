package com.tcs.training.ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHOR", uniqueConstraints = { @UniqueConstraint(name = "UC_AUTHOR", columnNames = { "emailAddress" }) })
public class Author implements Serializable {

	@Id
	Long authorId;

	String firstName;

	String lastName;

	String emailAddress;

	String bio;

	String country;

	@ManyToMany(mappedBy = "authors")
	private Set<Book> books;

}
