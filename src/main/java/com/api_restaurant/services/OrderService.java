package com.api_restaurant.services;

import com.api_restaurant.models.Client;
import com.api_restaurant.models.Order;
import com.api_restaurant.repositories.OrderRepository;
import com.api_restaurant.utils.*;
import com.api_restaurant.utils.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final FrequentClientHandler frequentClientHandler;
    private final DiscountStrategy noDiscountStrategy;
    private final DiscountStrategy frequentClientDiscountStrategy;
    private final PopularDishHandler popularDishHandler;
    private final PriceStrategy regularPriceStrategy;
    private final PriceStrategy popularDishPriceStrategy;

    @Autowired
    public OrderService(OrderRepository repository, FrequentClientHandler frequentClientHandler, NoDiscountStrategy noDiscountStrategy, FrequentClientDiscountStrategy frequentClientDiscountStrategy, PopularDishHandler popularDishHandler, RegularPriceStrategy regularPriceStrategy, PopularDishPriceStrategy popularDishPriceStrategy) {
        this.repository = repository;
        this.frequentClientHandler = frequentClientHandler;
        this.noDiscountStrategy = noDiscountStrategy;
        this.frequentClientDiscountStrategy = frequentClientDiscountStrategy;
        this.popularDishHandler = popularDishHandler;
        this.regularPriceStrategy = regularPriceStrategy;
        this.popularDishPriceStrategy = popularDishPriceStrategy;
    }

    public void addOrder(Order order) {
        Client client = order.getClient();
        frequentClientHandler.handle(client);

        Double total = order.getDishes().stream().mapToDouble(dish -> {
            popularDishHandler.handle(dish);
            PriceStrategy priceStrategy = dish.getSpecialDish() ? popularDishPriceStrategy : regularPriceStrategy;
            return priceStrategy.applyPrice(dish.getPrice());
        }).sum();

        DiscountStrategy discountStrategy = client.getFrequent() ? frequentClientDiscountStrategy : noDiscountStrategy;
        total = discountStrategy.applyDiscount(total);

        order.setTotal(total);
        repository.save(order);
    }

    public Optional<Order> getOrder(Long id) {
        return repository.findById(id);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order updateOrder(Long id, Order order) {
        return repository.findById(id).map(x -> {
            x.setClient(order.getClient());
            x.setDishes(order.getDishes());
            x.setTotal(order.getTotal());
            return repository.save(x);
        }).orElseThrow(() -> {
            return new RuntimeException("Order with id " + id + " could not be updated");
        });
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

}