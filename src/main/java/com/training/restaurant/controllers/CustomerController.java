package com.training.restaurant.controllers;

import com.training.restaurant.dto.customer.CustomerDto;
import com.training.restaurant.dto.customer.CreateCustomerDto;
import com.training.restaurant.dto.customer.UpdateCustomerDto;
import com.training.restaurant.services.customer.CustomerServiceImpl;
import com.training.restaurant.utils.CustomerConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createClient(@Valid @RequestBody CreateCustomerDto createCustomerDto){
        CustomerDto customerDto = CustomerConverter.toCustomerDto(customerService.createCustomer(createCustomerDto));
        URI uri = UriComponentsBuilder.fromUriString("/products/{id}").buildAndExpand(customerDto.id()).toUri();

        return ResponseEntity.created(uri).body(customerDto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAllClients() {
        return ResponseEntity.ok(customerService.findAllCustomers().stream().map(CustomerConverter::toCustomerDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findClientById(@PathVariable Long id){
        return ResponseEntity.ok(CustomerConverter.toCustomerDto(customerService.findCustomerById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,
                                                 @Valid @RequestBody UpdateCustomerDto updateCustomerDto){
        customerService.updateCustomer(id, updateCustomerDto);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.removeCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
