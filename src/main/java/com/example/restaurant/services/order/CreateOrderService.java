package com.example.restaurant.services.order;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.OrderSubject;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.client.GetClientByIdService;
import com.example.restaurant.services.dish.GetDishByIdService;
import com.example.restaurant.services.interfaces.ICommandModifier;
import com.example.restaurant.utils.OrderPriceCalculator;
import com.example.restaurant.handlers.OrderProcessingChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateOrderService implements ICommandModifier<Order, List<Long>> {

    private final IOrderRepository orderRepository;
    private final GetClientByIdService getClientByIdService;
    private final GetDishByIdService getDishByIdService;
    private final OrderSubject orderSubject;
    private final OrderProcessingChain orderProcessingChain;

    @Autowired
    public CreateOrderService(IOrderRepository orderRepository, GetClientByIdService getClientByIdService, GetDishByIdService getDishByIdService, OrderSubject orderSubject, OrderProcessingChain orderProcessingChain) {
        this.orderRepository = orderRepository;
        this.getClientByIdService = getClientByIdService;
        this.getDishByIdService = getDishByIdService;
        this.orderSubject = orderSubject;
        this.orderProcessingChain = orderProcessingChain;
    }

    @Override
    public Order execute(Long clientId, List<Long> dishIds) {
        Client client = getClientByIdService.execute(clientId);

        List<Dish> dishes = dishIds.stream()
                .map(getDishByIdService::execute)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setClient(client);
        order.setDishes(dishes);
        order.setOrderDate(LocalDateTime.now());

        orderProcessingChain.process(order);

        float totalPrice = OrderPriceCalculator.calculateTotalPrice(dishes, client.getClientType());
        order.setTotalPrice(totalPrice);

        Order createdOrder = orderRepository.save(order);
        orderSubject.notifyObservers(EventType.CREATE, createdOrder);
        return createdOrder;
    }
}
