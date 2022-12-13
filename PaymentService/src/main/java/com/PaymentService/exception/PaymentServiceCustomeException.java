package com.PaymentService.exception;

import lombok.Data;

@Data
public class PaymentServiceCustomeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8873085340764853776L;
	
	private String errorCode;

	public PaymentServiceCustomeException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	
}
