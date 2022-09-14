package com.loyalty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.model.Customer;
import com.loyalty.model.Points;
import com.loyalty.service.LoyaltyPointService;

@RestController
@RequestMapping("/loyaltyPoints")
public class LoyaltyPointCalculaterController {
	

	@Autowired
	private LoyaltyPointService pointsServices;

	 @GetMapping(value = "/customers/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Object> getPointsByCustomerId(@PathVariable("customerId") Long customerId){
	        Customer customer = pointsServices.getCustomerByCustomerId(customerId.intValue());
	        if(customer == null)
	        { 
	        	return new ResponseEntity<>("No Customer Found with this ID",HttpStatus.OK); 
	        }
	        Points points = pointsServices.getPointsByCustomerId(customerId);
	        return new ResponseEntity<>(points,HttpStatus.OK);
	    }
  
}
