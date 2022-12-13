package com.ProductService.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProductService.entity.Product;
import com.ProductService.exception.ProductServiceCustomeException;
import com.ProductService.model.ProductResponse;
import com.ProductService.repo.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Long createProduct(Product product) {
		Product product2 = productRepo.save(product);
		return product2.getProductId();
	}

	@Override
	public ProductResponse getProduct(Long id) {
		ProductResponse productResponse = new ProductResponse();
		Product product = productRepo.findById(id)
				.orElseThrow(
				() -> new ProductServiceCustomeException(
						"ProductNotFound", "PRODUCT_NOT_FOUND"));
		
		BeanUtils.copyProperties(product, productResponse);
		return productResponse;
	}

	//UPDATE THE PRODUCT QUANTITY
	@Override
	public void updateQuntity(long id, long quantity) {
		//GET PRODUCT BY ID
		Product product = productRepo.findById(id)
				.orElseThrow(()->
				new ProductServiceCustomeException(
						"Product with this id is not present","PRODUCT_NOT_FOUND" ));
		
		//CHECK QUANTITY ENOUGHT TO PURCHASE OR NOT
		if (product.getQuantity()<quantity) {
			throw new 
			ProductServiceCustomeException(
					"Not enough quantity available", "INSUFFICIENT_PRODUCT_QUANTITY");
		}
		
		//IF ENOUGHT QUANTITY IS PRESENT--AVAILABLE TO PURCHASE, THEN UPDATE IT
		product.setQuantity(product.getQuantity()-quantity);
		productRepo.save(product);
		log.info("Product quantity updated.");
	}

}
