package com.tcs.training.ms.exception;

import com.tcs.training.ms.model.exception.Problem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GenericRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Problem> handleNoSuchElementException(Exception ex, HttpServletRequest request) {
		return new ResponseEntity<>(Problem.builder()
			.title("No Data Found")
			.status(HttpStatus.NOT_FOUND.value())
			.type(request.getContextPath())
			.detail("Requested data not found")
			.instance(request.getRequestURI())
			.build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
