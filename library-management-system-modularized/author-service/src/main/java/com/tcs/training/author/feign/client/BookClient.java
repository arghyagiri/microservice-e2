package com.tcs.training.author.feign.client;

import com.tcs.training.author.feign.model.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "BOOK-SERVICE",
        path = "/books"/*
						 * name = "${feign.bookClient.name}" , url =
						 * "${feign.bookClient.url}", configuration =
						 * BookClientErrorDecoder.class
						 */)
public interface BookClient {

    @GetMapping("/find-by-author/{authorId}")
    List<BookDTO> getBooks(@PathVariable("authorId") Long authorId);

}
