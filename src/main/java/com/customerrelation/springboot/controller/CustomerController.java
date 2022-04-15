package com.customerrelation.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customerrelation.springboot.model.Customer;
import com.customerrelation.springboot.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/showAllCustomers")
	public List<Customer> showAll() {
		return customerRepository.findAll();

	}
	
	
/*
	@GetMapping("/showAllCustomersByAddress/{address}")
	public List<Customer> showAllByAddress(@PathVariable("address") String address) {
		
		return customerRepository.findByAddress(address);

	}*/
	
	@GetMapping("/showAllCustomersByAddress/{address}")
	public ResponseEntity<Object> showAllByAddress(@PathVariable("address") String address) {
		
		List<Customer> custList =  customerRepository.findByAddress(address);
		
		if(custList.isEmpty())
			return ResponseEntity.ok("NO record found with "+address+" address");
		else
		return ResponseEntity.ok(custList);
		

	}
	
	
	
	@PostMapping("/addCustomer")
	public String addCustomer(@RequestBody Customer customer) {

		customerRepository.save(customer);
		return "Record added successfully";
	}

	@GetMapping("/searchCustomer/{custId}")
	public ResponseEntity<?> searchCustomer(@PathVariable("custId") int custId) {

		try {
			Customer customer = customerRepository.findById(custId).get();
			return ResponseEntity.ok(customer);
		} catch (Exception e) {
			return ResponseEntity.ok("No record found with " + custId);
		}
	}

	@GetMapping("/test/mine")
	public String getMessage(){
		return " added into repository";
	}

	@DeleteMapping("/deleteCustomer/{custId}")
	public String deleteCustomer(@PathVariable("custId") int custId) {

		try {
			Customer customer = customerRepository.findById(custId).get();

			customerRepository.delete(customer);

			return "Record deleted successfully";
		} catch (Exception e) {
			return "No record found with " + custId;
		}
	}

	@PutMapping("/updateCustomer")
	public String updateCustomer(@RequestBody Customer cust) {

		try {
			Customer customer = customerRepository.findById(cust.getCustomerId()).get();

			customer.setCustomerName(cust.getCustomerName()); //  latest customername
			customer.setAddress(cust.getAddress());           // latest address			
			customerRepository.save(customer);			
			
			return "Record updated successfully";
		} catch (Exception e) {
			return "No record found with " + cust.getCustomerId();
		}
	}

}
