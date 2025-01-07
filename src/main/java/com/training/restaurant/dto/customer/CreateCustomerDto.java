package com.training.restaurant.dto.customer;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCustomerDto(
        @NotBlank
        @JsonAlias("client_name")
        String name,

        @NotBlank
        @Email
        @JsonAlias("client_email")
        String email,

        @NotNull
        @Positive
        @JsonAlias("client_age")
        Integer age,
        @NotBlank
        @JsonAlias("client_phone")
        String phone,
        @NotBlank
        @JsonAlias("client_address")
        String address
) {
}
