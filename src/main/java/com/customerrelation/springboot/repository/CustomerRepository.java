package com.customerrelation.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.customerrelation.springboot.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	
	@Query("from Customer as C where C.address = ?1") // customized query
	List<Customer>  findByAddress(String address);
}
