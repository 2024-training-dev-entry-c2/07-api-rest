package com.training.restaurant.utils;

import com.training.restaurant.dto.orders.OrdersDto;
import com.training.restaurant.models.Customer;
import com.training.restaurant.models.Orders;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OrdersConverter {

    public static Orders toOrders(Customer customer) {
        return new Orders(
                ZonedDateTime.now(ZoneId.of("America/Caracas")),
                customer
        );
    }

    public static OrdersDto toOrdersDto(Orders orders) {
        return new OrdersDto(orders);
    }

}
