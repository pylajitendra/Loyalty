package com.loyalty.util;

import java.util.ArrayList;
import java.util.List;

import com.loyalty.model.Customer;

public class CustomerData {

	
	public static List<Customer> getAllCustomers() {
		List<Customer> customers=new ArrayList<>();	


		Customer customer1=new Customer(1l,"Micheal");		
		customers.add(customer1);

		Customer customer2=new Customer(2l,"Sachin");		
		customers.add(customer2);

		Customer customer3=new Customer(3l,"Shane");
		customers.add(customer3);

		return customers;
	}


	

	
}
