package com.tcs.training.borrowing.feign.client;

import com.tcs.training.borrowing.feign.exception.BookClientErrorDecoder;
import com.tcs.training.borrowing.feign.model.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "${feign.bookClient.name}", /* url = "${feign.bookClient.url}", */
		configuration = BookClientErrorDecoder.class, path = "/books")
public interface BookClient {

	@GetMapping(value = "/get-by-ids")
	Set<BookDTO> getBooks(@RequestParam("id") Set<Long> ids);

	@PostMapping(value = "/update-status")
	BookDTO updateBookStatus(@RequestBody BookDTO bookDTO);

}
