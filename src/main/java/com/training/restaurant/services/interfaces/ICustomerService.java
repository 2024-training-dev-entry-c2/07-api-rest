package com.training.restaurant.services.interfaces;

import com.training.restaurant.dto.customer.CreateCustomerDto;
import com.training.restaurant.dto.customer.UpdateCustomerDto;
import com.training.restaurant.models.Customer;

import java.util.List;

public interface ICustomerService {
    Customer createCustomer(CreateCustomerDto createCustomerDto);
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Customer findCustomerById(Long id);
    void updateCustomer(Long id, UpdateCustomerDto updateCustomerDto);
    void removeCustomer(Long id);
}
