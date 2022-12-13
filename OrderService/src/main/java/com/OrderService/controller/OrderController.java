package com.OrderService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderService.model.OrderRequest;
import com.OrderService.model.OrderResponse;
import com.OrderService.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public String hello() {
		return "Order-service";
	}

	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping("/placeOrder")
	public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
		long productId = orderService.placeOrder(orderRequest);
		return new ResponseEntity<>(productId, HttpStatus.OK);

	}
	
	//GET ORDER DETAILS
	@PreAuthorize("hasAuthority('Customer') || hasAuthority('Admin')")
	@GetMapping("/getOrder/{orderId}")
	public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId ){
		return new ResponseEntity<OrderResponse>(orderService.getOrderDetails(orderId), HttpStatus.OK);
	}


}
