package com.tcs.training.ms.controller;

import com.tcs.training.ms.entity.Genre;
import com.tcs.training.ms.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class GenreController {

	private final GenreRepository genreRepository;

	@GetMapping
	public List<Genre> getAll() {
		return genreRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public Genre getById(@PathVariable("id") Long id) {
		return genreRepository.findById(id).orElseThrow();
	}

	@PostMapping()
	public Genre add(@RequestBody Genre genre) {
		return genreRepository.save(genre);
	}

	@PutMapping()
	public Genre put(@RequestBody Genre genre) {
		return genreRepository.save(genre);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		genreRepository.deleteById(id);
	}

}
