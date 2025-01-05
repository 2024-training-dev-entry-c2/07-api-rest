package com.example.restaurant.mapper;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.models.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerMapper {

  public CustomerDTO toDTO(Customer customer) {
    return new CustomerDTO(
            customer.getId(),
            customer.getName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getOrders() != null ? customer.getOrders()
                    .stream()
                    .map(order -> new OrderMapper().toDTO(order))
                    .collect(Collectors.toList()) : null
    );
  }

  public Customer toEntity(CustomerDTO customerDTO) {
    return new Customer(
            customerDTO.getId(),
            customerDTO.getName(),
            customerDTO.getLastName(),
            customerDTO.getEmail(),
            customerDTO.getPhone(),
            null // Evitamos mapear la lista de pedidos al crear un cliente
    );
  }
}
