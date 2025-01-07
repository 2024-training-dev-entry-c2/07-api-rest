package com.training.restaurant.utils;

import com.training.restaurant.dto.customer.CustomerDto;
import com.training.restaurant.dto.customer.CreateCustomerDto;
import com.training.restaurant.models.Customer;
import com.training.restaurant.models.CustomerType;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CustomerConverter {

    public static Customer toCreateACustomer(CreateCustomerDto createCustomerDto) {
        return new Customer(
                createCustomerDto.name(),
                createCustomerDto.email(),
                CustomerType.COMMON.typeToString(),
                createCustomerDto.age(),
                createCustomerDto.phone(),
                createCustomerDto.address(),
                ZonedDateTime.now(ZoneId.of("America/Caracas")));
    }


    public static CustomerDto toCustomerDto(Customer customer){
        return new CustomerDto(customer);
    }
}
