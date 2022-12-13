package com.OrderService.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.OrderService.entity.Order;
import com.OrderService.exception.OrderServiceCustomeException;
import com.OrderService.external.client.PaymentService;
import com.OrderService.external.client.ProductService;
import com.OrderService.external.request.PaymentRequest;
import com.OrderService.model.OrderRequest;
import com.OrderService.model.OrderResponse;
import com.OrderService.model.PaymentResponse;
import com.OrderService.model.ProductResponse;
import com.OrderService.repo.OrderRepository;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImp implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public long placeOrder(OrderRequest orderRequest) {
	
		//CREATE A ORDER
		//PRODUCT-SERVICE: update quantity
		//PAYMENT-SERVICE: 
		
		//CALLING PRODUCT SERVICE: Check if enough quantity is available or not, to place the order.
		productService.updateQuntity(orderRequest.getProductId(), orderRequest.getQuantity());
		
		//PLACING THE ORDER
		log.info("Placing order");
		Order order = Order.builder()
				.productId(orderRequest.getProductId())
				.quantity(orderRequest.getQuantity())
				.orderDate(Instant.now())
				.orderStatus("CREATED")
				.amount(orderRequest.getTotalAmount())
				.build();
		order = orderRepository.save(order);
		log.info("Order placed.");
		
		//CALLING PAYMENT SERVICE: To make payment after placing the order
		log.info("Calling payment service to complete the payment");
		
		PaymentRequest paymentRequest = PaymentRequest.builder()
				.orderId(order.getOrderId())
				.paymentMode(orderRequest.getPaymentMode())
				.referanceNumber(UUID.randomUUID().toString())
				.amount(orderRequest.getTotalAmount())
				.build();
		/*
		 Note:
		 	1. If payment is failed, the reduced quantity must be reverted back. 
		    2. If payment is failed for multiple times, for a customer, make sure to remove the rows, 
		       from the data base multiple placeOrder for same order.
		       (this may vary based on the requirements) 
		 */
		
		
		
		//CHECKING OF ANY EXCEPTION WHILE MAKING PAYMENT
		String orderStatus = null;
		try {
			paymentService.makePayment(paymentRequest);
			log.info("Payment done successfully, changing order status to placed");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.info("Error occured while making payment");
			orderStatus = "PAYMENT_FAILED";
		}
		
		
		//UPDATING ORDER STATUS: After successful payment. 
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);
		
		return order.getOrderId();
	}

	//GET ORDER DETAILS
	@Override
	public OrderResponse getOrderDetails(long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				()-> new OrderServiceCustomeException(
						"Order does not exist for this id", "ORDER_NOT_FOUND", 404));
		
		log.info("Connecting to ProductService to get the product details by id: {}"
				, order.getProductId());
		
		//PRODUCT DETAILS
		//getting product details from product-service by id
		ProductResponse productResponse =
				restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId()
				, ProductResponse.class);
		
		//adding product details
		OrderResponse.ProductDetails productDetails = new OrderResponse.ProductDetails();
		productDetails.setProductId(productResponse.getProductId());
		productDetails.setProductName(productResponse.getProductName());
		productDetails.setQuantity(productResponse.getQuantity());
		productDetails.setPrice(productResponse.getPrice());
		
		
		//PAYMENT DETAILS
		//getting product details from product-service by id
		PaymentResponse payment =
					restTemplate.getForObject(
							"http://PAYMENT-SERVICE/payment/getDetails/"+order.getOrderId()
					, PaymentResponse.class);
		
		OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
				.amount(payment.getAmount())
				.orderId(payment.getOrderId())
				.paymentDate(payment.getPaymentDate())
				.paymentMode(payment.getPaymentMode())
				.paymentStatus(payment.getPaymentStatus())
				.transactionId(payment.getTransactionId())
				.regeranceNumber(payment.getRegeranceNumber())
				.build();
			
		OrderResponse orderResponse = OrderResponse.builder()
				.orderId(order.getOrderId())
				.orderDate(order.getOrderDate())
				.orderStatus(order.getOrderStatus())
				.amount(order.getAmount())
				.productDetails(productDetails)
				.paymentDetails(paymentDetails)
				.build();
		
		return orderResponse;
	}

}
