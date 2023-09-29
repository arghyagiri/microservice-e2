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
@Table(name = "GENRE", uniqueConstraints = { @UniqueConstraint(name = "UC_GENRE", columnNames = { "genreName" }) })
public class Genre implements Serializable {

	@Id
	private Long genreId;

	private String genreName;

	@ManyToMany(mappedBy = "genres")
	private Set<Book> books;

}
