package com.OrderService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OrderService.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
