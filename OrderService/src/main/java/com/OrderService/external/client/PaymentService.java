package com.OrderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.OrderService.exception.OrderServiceCustomeException;
import com.OrderService.external.request.PaymentRequest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

	@PostMapping("/makePayment")
	public ResponseEntity<Long> makePayment(@RequestBody PaymentRequest request);

	default void fallback(Exception e) {
		throw new OrderServiceCustomeException("Payment Service is down.", "UNAVAILABLE", 500);
	}

}
