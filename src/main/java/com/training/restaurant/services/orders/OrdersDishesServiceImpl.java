package com.training.restaurant.services.orders;

import com.training.restaurant.dto.orders.CreateOrderDto;
import com.training.restaurant.models.Customer;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.OrderDish;
import com.training.restaurant.models.Orders;
import com.training.restaurant.services.customer.CustomerServiceImpl;
import com.training.restaurant.services.dishes.DishServiceImpl;
import com.training.restaurant.services.interfaces.IOrderDishesService;
import com.training.restaurant.utils.OrdersConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersDishesServiceImpl implements IOrderDishesService {

    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;
    private final DishServiceImpl dishService;

    @Autowired
    public OrdersDishesServiceImpl(CustomerServiceImpl customerService, OrderServiceImpl orderService, DishServiceImpl dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.customerService = customerService;
    }

    @Override
    public Orders createOrder(Long customerId, CreateOrderDto createOrderDto) {
        Customer customer = customerService.findCustomerById(customerId);
        Orders orders = OrdersConverter.toOrders(customer);
        orders.setOrderDishes(associateDishes(createOrderDto,orders));
        return orderService.createOrder(orders);

    }

    private List<OrderDish> associateDishes(CreateOrderDto createOrderDto, Orders order) {
        return createOrderDto.dishes().stream().map(orderDishDto -> {
            Dishes dish = dishService.findDishByName(orderDishDto.name());
            OrderDish orderDish = new OrderDish();
            orderDish.setOrder(order);
            orderDish.setDish(dish);
            orderDish.setQuantity(orderDishDto.quantity());
            return orderDish;
        }).toList();
    }
}
