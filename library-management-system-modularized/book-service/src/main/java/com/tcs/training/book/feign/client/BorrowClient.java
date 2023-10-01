package com.tcs.training.book.feign.client;

import com.tcs.training.book.feign.exception.BorrowClientErrorDecoder;
import com.tcs.training.book.feign.model.BorrowingRequest;
import com.tcs.training.book.feign.model.BorrowingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${feign.borrowClient.name}", url = "${feign.borrowClient.url}",
		configuration = BorrowClientErrorDecoder.class)
public interface BorrowClient {

	@PostMapping(value = "/init")
	List<BorrowingResponse> getBorrowingRecords(@RequestBody BorrowingRequest borrowingRequest);

	@PostMapping(value = "/return")
	List<BorrowingResponse> getReturnRecords(@RequestBody BorrowingRequest borrowingRequest);

}
