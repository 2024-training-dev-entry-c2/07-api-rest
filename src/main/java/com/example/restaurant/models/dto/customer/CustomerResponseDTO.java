package com.example.restaurant.models.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomerResponseDTO {
    private Long customerId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private List<Long> orderIds;
}
