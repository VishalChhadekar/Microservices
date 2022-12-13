package com.PaymentService.service;

import java.time.Instant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PaymentService.entity.TransactionDetails;
import com.PaymentService.exception.PaymentServiceCustomeException;
import com.PaymentService.model.PaymentRequest;
import com.PaymentService.model.PaymentResponse;
import com.PaymentService.repo.PaymentServiceRepo;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class PaymentServiceImp implements PaymentService{
	
	@Autowired
	private PaymentServiceRepo paymentServiceRepo;

	@Override
	public long makePaymetn(PaymentRequest request) {
		log.info("Recording payment details: {}", request);
		
		TransactionDetails transactionDetails = 
				TransactionDetails.builder()
				.paymentDate(Instant.now())
				.paymentMode(request.getPaymentMode().name())
				/*
				as setPaymentMode of Transaction details takes string as argument, 
				thus, we have to use .name() From object to string.
				*/
				.regeranceNumber(request.getReferanceNumber())
				.amount(request.getAmount())
				.paymentStatus("SUCCESS")
				.orderId(request.getOrderId())
				.build();
		paymentServiceRepo.save(transactionDetails);
		log.info("Transaction completed with id: {}", transactionDetails.getTransactionId());
		return transactionDetails.getTransactionId();
	}

	@Override
	public PaymentResponse getPaymentDetails(long id) {
		TransactionDetails payment = paymentServiceRepo.findByOrderId(id);
		if(payment==null) {
			throw new PaymentServiceCustomeException(
					"Transaction with this ID is not present", "PAYMENT_NOT_FOUND");
		}

		
		PaymentResponse paymentResponse = PaymentResponse.builder()
				.amount(payment.getAmount())
				.transactionId(payment.getTransactionId())
				.orderId(payment.getOrderId())
				.paymentDate(payment.getPaymentDate())
				.paymentMode(payment.getPaymentMode())
				.regeranceNumber(payment.getRegeranceNumber())
				.paymentStatus(payment.getPaymentStatus())
				.build();
		return paymentResponse;
	}

}
