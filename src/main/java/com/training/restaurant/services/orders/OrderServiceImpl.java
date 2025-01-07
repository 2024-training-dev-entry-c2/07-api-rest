package com.training.restaurant.services.orders;

import com.training.restaurant.models.Customer;
import com.training.restaurant.events.NewOrderEvent;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.Orders;
import com.training.restaurant.repositories.OrdersRepository;
import com.training.restaurant.services.interfaces.IOrderService;
import com.training.restaurant.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrdersRepository ordersRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository, ApplicationEventPublisher eventPublisher) {
        this.ordersRepository = ordersRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Orders createOrder(Orders orders) {
        try{
            Orders savedOrder = ordersRepository.save(orders);
            eventPublisher.publishEvent(new NewOrderEvent(this,savedOrder));
            return savedOrder;
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("Ocurrio un error al intentar guardar la orden");
        }
    }

    @Override
    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders findOrderById(Long id) {
        return ordersRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro la orden con id " + id));
    }

    @Override
    public void deleteOrder(Long id){
        ordersRepository.delete(findOrderById(id));
    }

    @Override
    public Long findCountOrdersByCustomer(Customer customer) {
        return ordersRepository.countByCustomer(customer);
    }

    @Override
    public Long countDishPurchases(Dishes dish) {
        return ordersRepository.countDishPurchases(dish.getId());
    }
}
