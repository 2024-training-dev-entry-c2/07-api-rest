package com.example.restaurant.mappers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

  public Customer toEntity(CustomerRequestDTO dto) {
    return new Customer(
            dto.getName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhone()
    );
  }

  public CustomerResponseDTO toDTO(Customer customer) {
    CustomerResponseDTO dto = new CustomerResponseDTO();
    dto.setId(customer.getId());
    dto.setName(customer.getName());
    dto.setLastName(customer.getLastName());
    dto.setEmail(customer.getEmail());
    dto.setPhone(customer.getPhone());
    dto.setOrderIds(customer.getOrders().stream()
            .map(Order::getId)
            .toList());
    return dto;
  }
}
