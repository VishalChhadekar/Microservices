package com.PaymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PaymentService.model.PaymentRequest;
import com.PaymentService.model.PaymentResponse;
import com.PaymentService.service.PaymentService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/payment")
@Log4j2
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/")
	public String payment() {
		return "Payment controller";
	}

	@PostMapping("/makePayment")
	public ResponseEntity<Long> makePayment(@RequestBody PaymentRequest request) {
		log.info("Making payment");
		long transactionId = paymentService.makePaymetn(request);
		return new ResponseEntity<>(transactionId, HttpStatus.OK);
	}
	
	//get payment details
	@GetMapping("/getDetails/{id}")
	public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable long id){
		return new ResponseEntity<PaymentResponse>(paymentService.getPaymentDetails(id), HttpStatus.OK);
	}

}
