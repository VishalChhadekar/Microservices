package com.PaymentService.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
	private long transactionId;
	private long orderId;
	private String paymentMode;
	private String regeranceNumber;
	private Instant paymentDate;
	private String paymentStatus;
	private long amount;
}
