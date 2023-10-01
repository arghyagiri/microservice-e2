package com.tcs.training.borrowing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BorrowingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowingApplication.class, args);
	}

}
