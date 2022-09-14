package com.loyalty.service;

import com.loyalty.model.Customer;
import com.loyalty.model.Points;



public interface LoyaltyPointService {
    public Points getPointsByCustomerId(Long customerId);
    public Customer getCustomerByCustomerId(int customerId);

}
