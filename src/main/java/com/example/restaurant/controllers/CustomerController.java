package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.restaurant.services.customer.CustomerCommandHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerCommandHandler customerCommandHandler;

  @PostMapping
  public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(customerCommandHandler.createCustomer(customerDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerDTO) {
    return ResponseEntity.ok(customerCommandHandler.updateCustomer(id, customerDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerCommandHandler.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
    return ResponseEntity.ok(customerCommandHandler.getCustomerById(id));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDTO>> listCustomers() {
    return ResponseEntity.ok(customerCommandHandler.listCustomers());
  }
}
