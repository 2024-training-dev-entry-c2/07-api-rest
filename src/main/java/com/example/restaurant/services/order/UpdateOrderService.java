package com.example.restaurant.services.order;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.OrderSubject;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import com.example.restaurant.utils.OrderPriceCalculator;
import com.example.restaurant.handlers.OrderProcessingChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService implements ICommandModifier<Order, Order> {

    private final IOrderRepository orderRepository;
    private final OrderSubject orderSubject;
    private final OrderProcessingChain orderProcessingChain;

    @Autowired
    public UpdateOrderService(IOrderRepository orderRepository, OrderSubject orderSubject, OrderProcessingChain orderProcessingChain) {
        this.orderRepository = orderRepository;
        this.orderSubject = orderSubject;
        this.orderProcessingChain = orderProcessingChain;
    }

    @Override
    public Order execute(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));

        existingOrder.setClient(updatedOrder.getClient());
        existingOrder.setDishes(updatedOrder.getDishes());

        orderProcessingChain.process(existingOrder);

        float totalPrice = OrderPriceCalculator.calculateTotalPrice(existingOrder.getDishes(), existingOrder.getClient().getClientType());
        existingOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(existingOrder);
        orderSubject.notifyObservers(EventType.UPDATE, savedOrder);

        return savedOrder;
    }
}
