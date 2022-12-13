package com.OrderService.model;

import java.time.Instant;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

	private long orderId;
	private Instant orderDate;
	private String orderStatus;
	private long amount;
	private ProductDetails productDetails;
	private PaymentDetails paymentDetails;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class  ProductDetails {
		
		private long productId;
		private String productName;
		private long price;
		private long quantity;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class PaymentDetails {
		private long transactionId;
		private long orderId;
		private String paymentMode;
		private String regeranceNumber;
		private Instant paymentDate;
		private String paymentStatus;
		private long amount;
	}
	
}
