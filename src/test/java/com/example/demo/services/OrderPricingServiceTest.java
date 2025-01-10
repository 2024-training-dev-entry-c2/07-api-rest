package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.models.Order;
import com.example.demo.rules.FrequentClientDiscountHandler;
import com.example.demo.rules.PopularDishPriceIncreaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderPricingServiceTest {
    @Mock
    private FrequentClientDiscountHandler frequentHandler;

    @Mock
    private PopularDishPriceIncreaseHandler popularHandler;

    @InjectMocks
    private OrderPricingService orderPricingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateTotalPrice_ShouldReturnSumOfDishPrices() {

        List<Dishfood> dishes = getDishfoodList();
        double totalPrice = orderPricingService.calculateTotalPrice(dishes);
        assertEquals(49.5, totalPrice, 0.01);
    }

    @Test
    void applyPricingRules_ShouldInvokeFrequentHandler() {
        Order order = new Order();

        orderPricingService.applyPricingRules(order);

        verify(frequentHandler, times(1)).applyRule(order);
    }

    private List<Dishfood> getDishfoodList() {
        Menu menu = new Menu(1L, "Menu prueba", List.of());
        return List.of(
                Dishfood.builder().id(1L).name("Pasta").price(12.5).isPopular(false).menu(menu).build(),
                Dishfood.builder().id(2L).name("pizza").price(17.5).isPopular(true).menu(menu).build(),
                Dishfood.builder().id(3L).name("hamburguesa").price(19.5).isPopular(false).menu(menu).build()

        );

    }

}