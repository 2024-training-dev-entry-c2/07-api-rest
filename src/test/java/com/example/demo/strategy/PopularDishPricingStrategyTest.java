package com.example.demo.strategy;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopularDishPricingStrategyTest {
    private PopularDishPricingStrategy pricingStrategy;

    @BeforeEach
    void setUp() {
        pricingStrategy = new PopularDishPricingStrategy();
    }

    @Test
    void apply_ShouldIncreaseTotalPriceForPopularDishes() {

        Dishfood popularDish1 = Dishfood.builder().id(1L).name("Pizza").price(20.0).isPopular(true).build();
        Dishfood popularDish2 = Dishfood.builder().id(2L).name("Burger").price(15.0).isPopular(true).build();
        Dishfood regularDish = Dishfood.builder().id(3L).name("Pasta").price(10.0).isPopular(false).build();

        Order order = Order.builder()
                .id(1L)
                .dishfoods(List.of(popularDish1, popularDish2, regularDish))
                .totalPrice(45.0)
                .build();

        pricingStrategy.apply(order);

        double expectedIncrease = (20.0 * 0.0573) + (15.0 * 0.0573);
        double expectedTotalPrice = 45.0 + expectedIncrease;

        assertEquals(expectedTotalPrice, order.getTotalPrice(), 0.01);
    }

    @Test
    void apply_ShouldNotChangeTotalPriceIfNoPopularDishes() {

        Dishfood regularDish1 = Dishfood.builder().id(1L).name("Salad").price(10.0).isPopular(false).build();
        Dishfood regularDish2 = Dishfood.builder().id(2L).name("Soup").price(15.0).isPopular(false).build();


        Order order = Order.builder()
                .id(2L)
                .dishfoods(List.of(regularDish1, regularDish2))
                .totalPrice(25.0)
                .build();


        pricingStrategy.apply(order);


        assertEquals(25.0, order.getTotalPrice());
    }

    @Test
    void apply_ShouldHandleEmptyDishList() {

        Order order = Order.builder()
                .id(3L)
                .dishfoods(List.of())
                .totalPrice(0.0)
                .build();

        pricingStrategy.apply(order);


        assertEquals(0.0, order.getTotalPrice());
    }
}