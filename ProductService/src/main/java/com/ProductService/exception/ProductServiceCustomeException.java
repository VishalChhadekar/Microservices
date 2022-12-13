package com.ProductService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomeException extends RuntimeException {
	private static final long serialVersionUID = 9027017786317014084L;
	
	private String errorCode;

	public ProductServiceCustomeException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
