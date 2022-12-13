package com.OrderService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceCustomeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608651272411633175L;

	private String errorCode;
	private int status;

	public OrderServiceCustomeException(String message, String errorCode, int status) {
		super(message);
		this.errorCode = errorCode;
		this.status = status;
	}

}
