package com.ProductService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductService.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

}
