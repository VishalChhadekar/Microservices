package com.OrderService.service;

import com.OrderService.model.OrderRequest;
import com.OrderService.model.OrderResponse;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);


}
