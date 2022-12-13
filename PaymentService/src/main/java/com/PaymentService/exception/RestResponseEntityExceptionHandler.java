package com.PaymentService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.PaymentService.model.ErrorResponse;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(PaymentServiceCustomeException.class)
	public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(
			PaymentServiceCustomeException exception){
		log.info("Sending ErrorResponse When payment is not present.");		
		return new ResponseEntity<>(new ErrorResponse().builder()
				.errorMessage(exception.getMessage())
				.errorCode(exception.getErrorCode())
				.build(), HttpStatus.NOT_FOUND
				);
		
	}
	
}
