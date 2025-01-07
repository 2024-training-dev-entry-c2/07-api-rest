package com.training.restaurant.dto.customer;

import com.training.restaurant.models.Customer;

public record CustomerDto(
        Long id,
        String name,
        String email,
        String type,
        String phone
) {

    public CustomerDto(Customer customer){
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getType().typeToString(),
                customer.getPhone()
        );
    }
}
