package com.example.demo.rules;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.strategy.PopularDishPricingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PopularDishPriceIncreaseHandlerTest {

    @Test
    void applyRule_ShouldApplyStrategyAndCallNextHandler() {

        PopularDishPricingStrategy mockStrategy = mock(PopularDishPricingStrategy.class);
        OrderRule mockNextHandler = mock(OrderRule.class);
        PopularDishPriceIncreaseHandler handler = new PopularDishPriceIncreaseHandler(mockStrategy);
        handler.setNextHandler(mockNextHandler);
        Order order = new Order();
        Dishfood popularDish = new Dishfood();
        popularDish.setIsPopular(true);
        order.setDishfoods(Collections.singletonList(popularDish));
        order.setTotalPrice(100.0);

        handler.applyRule(order);
        verify(mockStrategy, times(1)).apply(order);
        verify(mockNextHandler, times(1)).applyRule(order);
    }

    @Test
    void applyRule_ShouldApplyStrategyAndSkipNextHandlerIfNull() {
        PopularDishPricingStrategy mockStrategy = mock(PopularDishPricingStrategy.class);
        PopularDishPriceIncreaseHandler handler = new PopularDishPriceIncreaseHandler(mockStrategy);
        Order order = new Order();
        order.setTotalPrice(100.0);
        handler.applyRule(order);
        verify(mockStrategy, times(1)).apply(order);

    }
}