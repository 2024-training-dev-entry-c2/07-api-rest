package com.example.restaurant.services.order;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.OrderSubject;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderService implements ICommandParametrized<Void, Long> {

    private final IOrderRepository orderRepository;
    private final OrderSubject orderSubject;

    @Autowired
    public DeleteOrderService(IOrderRepository orderRepository, OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.orderSubject = orderSubject;
    }

    @Override
    public Void execute(Long id) {
        Order orderToDelete = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));

        orderRepository.deleteById(id);
        orderSubject.notifyObservers(EventType.DELETE, orderToDelete);
        return null;
    }
}
