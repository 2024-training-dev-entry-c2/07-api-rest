package com.training.restaurant.dto.customer;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

public record UpdateCustomerDto(
        @JsonAlias("customer_name")
        String name,
        @Email
        @JsonAlias("customer_email")
        String email,
        @JsonAlias("customer_type")
        String type,
        @Positive
        @JsonAlias("customer_age")
        Integer age,
        @JsonAlias("customer_phone")
        String phone,
        @JsonAlias("customer_address")
        String address
) {
}
