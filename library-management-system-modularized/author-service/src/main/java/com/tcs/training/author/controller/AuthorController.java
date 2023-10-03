package com.tcs.training.author.controller;

import com.tcs.training.author.entity.Author;
import com.tcs.training.author.feign.model.BookDTO;
import com.tcs.training.author.model.exception.AuthorDTO;
import com.tcs.training.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Author getById(@PathVariable("id") Long id) {
        return authorService.getById(id);
    }

    @GetMapping(value = "/get-by-ids")
    public List<Author> getByIds(@RequestParam("id") Set<Long> ids) {
        return authorService.getByIds(ids);
    }

    @PostMapping()
    public Author add(@RequestBody AuthorDTO author) {
        Author authorEntity = new Author();
        BeanUtils.copyProperties(author, authorEntity);
        return authorService.add(authorEntity);
    }

    @PutMapping()
    public Author put(@RequestBody Author author) {
        return authorService.put(author);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        authorService.delete(id);
    }

    @GetMapping(value = "/books/{authorId}")
    public List<BookDTO> findBooksByAuthorId(@PathVariable("authorId") Long authorId) {
        return authorService.getBooks(authorId);
    }

}
