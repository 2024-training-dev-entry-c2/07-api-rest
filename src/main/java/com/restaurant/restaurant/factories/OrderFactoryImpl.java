package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.OrderModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderFactoryImpl implements OrderFactory {
  @Override
  public OrderModel createOrder(ClientModel client, List<DishModel> dishes) {
    OrderModel order = new OrderModel();
    order.setClient(client);
    order.setDishes(dishes);
    order.setDate(LocalDateTime.now());
    return order;
  }
}
