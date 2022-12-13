package com.PaymentService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PaymentService.entity.TransactionDetails;

@Repository
public interface PaymentServiceRepo extends JpaRepository<TransactionDetails, Long>{

	TransactionDetails findByOrderId(long id);

}
