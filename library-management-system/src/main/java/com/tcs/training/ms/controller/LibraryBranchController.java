package com.tcs.training.ms.controller;

import com.tcs.training.ms.entity.LibraryBranch;
import com.tcs.training.ms.repository.LibraryBranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("library-branches")
@RequiredArgsConstructor
public class LibraryBranchController {

	private final LibraryBranchRepository libraryBranchRepository;

	@GetMapping
	public List<LibraryBranch> getAll() {
		return libraryBranchRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public LibraryBranch getById(@PathVariable("id") Long id) {
		return libraryBranchRepository.findById(id).orElseThrow();
	}

	@PostMapping()
	public LibraryBranch add(@RequestBody LibraryBranch libraryBranch) {
		return libraryBranchRepository.save(libraryBranch);
	}

	@PutMapping()
	public LibraryBranch put(@RequestBody LibraryBranch libraryBranch) {
		return libraryBranchRepository.save(libraryBranch);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		libraryBranchRepository.deleteById(id);
	}

}
