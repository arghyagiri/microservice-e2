package com.tcs.training.ms.repository;

import com.tcs.training.ms.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
