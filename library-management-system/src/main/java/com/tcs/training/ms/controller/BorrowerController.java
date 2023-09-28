package com.tcs.training.ms.controller;

import com.tcs.training.ms.entity.Borrower;
import com.tcs.training.ms.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerRepository borrowerRepository;

    @GetMapping
    public List<Borrower> getAll() {
        return borrowerRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Borrower getById(@PathVariable("id") Long id) {
        return borrowerRepository.findById(id).orElse(null);
    }

    @PostMapping()
    public Borrower add(@RequestBody Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @PutMapping()
    public Borrower put(@RequestBody Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        borrowerRepository.deleteById(id);
    }
}
