package com.tcs.training.author.service;

import com.tcs.training.author.entity.Author;
import com.tcs.training.author.feign.client.BookClient;
import com.tcs.training.author.feign.model.BookDTO;
import com.tcs.training.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final BookClient bookClient;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    public List<Author> getByIds(Set<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    @Transactional
    public Author add(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author put(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public void delete(@PathVariable("id") Long id) {
        authorRepository.deleteById(id);
    }

    public List<BookDTO> getBooks(Long authorId) {
        return bookClient.getBooks(authorId);
    }

}
