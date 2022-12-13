package com.OrderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.OrderService.exception.OrderServiceCustomeException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {

	@PutMapping("/updateQuantity/{id}")
	public ResponseEntity<Void> updateQuntity(@PathVariable long id, @RequestParam long quantity);

	default void fallback(Exception e) {
		throw new OrderServiceCustomeException("Product Service is down.", "UNAVAILABLE", 500);
	}
}
