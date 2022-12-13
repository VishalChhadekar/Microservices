package com.OrderService.external.decoder;

import java.io.IOException;

import com.OrderService.exception.OrderServiceCustomeException;
import com.OrderService.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomeErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		ObjectMapper objectMapper = new ObjectMapper();
		log.info("{}", response.request().url());
		log.info("{}", response.request().headers());

		try {
			ErrorResponse errorResponse = 
					objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
			
			return new OrderServiceCustomeException(
					errorResponse.getErrorMessage(), errorResponse.getErrorCode(),response.status() );
			
		} catch (IOException e) {
			throw new OrderServiceCustomeException(
					"Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
		}

	}

}
