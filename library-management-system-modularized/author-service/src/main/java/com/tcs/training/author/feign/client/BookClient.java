package com.tcs.training.author.feign.client;

import com.tcs.training.author.feign.exception.BookClientErrorDecoder;
import com.tcs.training.author.feign.model.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${feign.bookClient.name}", url = "${feign.bookClient.url}",
		configuration = BookClientErrorDecoder.class)
public interface BookClient {

	@GetMapping("/find-by-author/{authorId}")
	List<BookDTO> getBooks(@RequestParam("authorId") Long authorId);

}
