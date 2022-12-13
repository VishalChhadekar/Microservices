package com.PaymentService.service;

import com.PaymentService.model.PaymentRequest;
import com.PaymentService.model.PaymentResponse;

public interface PaymentService {

	long makePaymetn(PaymentRequest request);

	PaymentResponse getPaymentDetails(long id);

}
