package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.rules.FrequentClientDiscountHandler;
import com.example.demo.rules.PopularDishPriceIncreaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderPricingService {
    private final FrequentClientDiscountHandler frequentHandler;
    private final PopularDishPriceIncreaseHandler popularHandler;

    @Autowired
    public OrderPricingService(FrequentClientDiscountHandler frequentHandler,
                               PopularDishPriceIncreaseHandler popularHandler) {
        this.frequentHandler = frequentHandler;
        this.popularHandler = popularHandler;
        this.frequentHandler.setNextHandler(popularHandler);
    }

    public double calculateTotalPrice(List<Dishfood> dishfoods) {
        return dishfoods.stream().mapToDouble(Dishfood::getPrice).sum();
    }

    public void applyPricingRules(Order order) {

        frequentHandler.applyRule(order);
    }
}
