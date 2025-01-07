package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.OrderModel;

import java.util.List;

public interface OrderFactory {
  OrderModel createOrder(ClientModel client, List<DishModel> dishes);
}
