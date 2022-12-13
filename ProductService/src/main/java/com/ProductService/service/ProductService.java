package com.ProductService.service;

import com.ProductService.entity.Product;
import com.ProductService.model.ProductResponse;

public interface ProductService {

	

	Long createProduct(Product product);

	ProductResponse getProduct(Long id);

	void updateQuntity(long id, long quantity);

}
