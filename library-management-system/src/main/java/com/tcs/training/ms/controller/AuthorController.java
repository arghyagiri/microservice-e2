package com.tcs.training.ms.controller;

import com.tcs.training.ms.entity.Author;
import com.tcs.training.ms.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

	private final AuthorRepository authorRepository;

	@GetMapping
	public List<Author> getAll() {
		return authorRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public Author getById(@PathVariable("id") Long id) {

		return authorRepository.findById(id).orElseThrow();
	}

	@PostMapping()
	public Author add(@RequestBody Author author) {
		return authorRepository.save(author);
	}

	@PutMapping()
	public Author put(@RequestBody Author author) {
		return authorRepository.save(author);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		authorRepository.deleteById(id);
	}

}
