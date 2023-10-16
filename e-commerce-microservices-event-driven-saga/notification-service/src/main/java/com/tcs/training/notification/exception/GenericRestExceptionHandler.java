package com.tcs.training.notification.exception;

import com.tcs.training.notification.model.exception.Problem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GenericRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Value("${spring.application.name}")
	String appName;

	@ExceptionHandler({ NoSuchElementException.class, NoDataFoundException.class })
	public ResponseEntity<Problem> handleNoSuchElementException(Exception ex, HttpServletRequest request) {
		return new ResponseEntity<>(Problem.builder()
			.title("No Data Found")
			.status(HttpStatus.NOT_FOUND.value())
			.type(request.getContextPath())
			.detail("[" + appName + "] >> " + ex.getMessage())
			.instance(request.getRequestURI())
			.build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
