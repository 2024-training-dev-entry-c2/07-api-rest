package com.example.restaurant.utils;

import com.example.restaurant.constants.ClientType;
import com.example.restaurant.constants.DishType;
import com.example.restaurant.models.Dish;

import java.util.List;
import java.util.Map;

public class OrderPriceCalculator {

    private static final Map<DishType, Float> DISH_TYPE_MULTIPLIER = Map.of(
            DishType.POPULAR, 1.0573F,
            DishType.COMMON, 1.0F
    );

    private static final Map<ClientType, Float> CLIENT_TYPE_DISCOUNT = Map.of(
            ClientType.FREQUENT, 0.9762F,
            ClientType.COMMON, 1.0F
    );

    public static float calculateTotalPrice(List<Dish> dishes, ClientType clientType) {
        float totalPrice = (float) dishes.stream()
                .mapToDouble(OrderPriceCalculator::priceBasedOnDishType)
                .sum();
        return priceBasedOnClientType(clientType, totalPrice);
    }

    private static float priceBasedOnDishType(Dish dish) {
        return dish.getPrice() * DISH_TYPE_MULTIPLIER.getOrDefault(dish.getDishType(), 1.0F);
    }

    private static float priceBasedOnClientType(ClientType clientType, float totalPrice) {
        return totalPrice * CLIENT_TYPE_DISCOUNT.getOrDefault(clientType, 1.0F);
    }
}
