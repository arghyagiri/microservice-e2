package com.tcs.training.book.feign.client;

import com.tcs.training.book.feign.model.AuthorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(/*
				 * name = "${feign.authorClient.name}", url =
				 * "${feign.authorClient.url}",configuration =
				 * AuthorClientErrorDecoder.class
				 */
        name = "AUTHOR-SERVICE", path = "/authors")
public interface AuthorClient {

    @RequestMapping(value = "/{authorId}")
    AuthorDTO getAuthor(@PathVariable("authorId") Long authorId);

    @GetMapping(value = "/get-by-ids")
    Set<AuthorDTO> getAuthors(@RequestParam("id") Set<Long> ids);

}
