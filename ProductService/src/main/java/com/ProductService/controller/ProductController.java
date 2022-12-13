package com.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProductService.entity.Product;
import com.ProductService.model.ProductResponse;
import com.ProductService.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<Long> createProduct(@RequestBody Product product){
		Long productId = productService.createProduct(product);
		return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_internal')")
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
		ProductResponse product = productService.getProduct(id);
		return new ResponseEntity<ProductResponse>(product, HttpStatus.OK);
	}
	
	@PutMapping("/updateQuantity/{id}")
	public ResponseEntity<Void> updateQuntity(@PathVariable long id, @RequestParam long quantity) {
		productService.updateQuntity(id, quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
