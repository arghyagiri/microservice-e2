package com.tcs.training.ms.repository;

import com.tcs.training.ms.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
