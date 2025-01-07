package com.training.restaurant.events.components;

import com.training.restaurant.events.NewOrderEvent;
import com.training.restaurant.models.Customer;
import com.training.restaurant.models.CustomerType;
import com.training.restaurant.models.Orders;
import com.training.restaurant.repositories.CustomerRepository;
import com.training.restaurant.services.orders.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomerTypeUpdater {

    private final CustomerRepository customerRepository;
    private final OrderServiceImpl orderService;

    @Autowired
    public CustomerTypeUpdater(CustomerRepository customerRepository, OrderServiceImpl orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @EventListener
    public void handleNewOrderEvent(NewOrderEvent event) {
        Orders order = event.getOrder();
        Customer customer = order.getCustomer();
        Long countOrders = orderService.findCountOrdersByCustomer(customer);
        log.info("Customer " + customer.getName() + " has " + countOrders + " orders");
        if (toCustomerUpdate(countOrders, customer)) {
            customer.setType(CustomerType.FREQUENT);
        }
        order.setTotal(calculateTotalWithDiscount(order, customer.getType() == CustomerType.FREQUENT));
        customerRepository.save(customer);
    }


    private Boolean toCustomerUpdate(Long countOrders, Customer customer) {
        return !(countOrders < 10) && customer.getType() != CustomerType.FREQUENT;
    }

    private Double calculateTotalWithDiscount(Orders order, Boolean applyDiscount) {
        Double total = order.getOrderDishes().stream()
                .mapToDouble(orderDish -> {
                    Double price = orderDish.getDish().getPrice();
                    return price * orderDish.getQuantity();
                }).sum();

        if (applyDiscount) {
            total *= 0.9762;
            order.setDiscount(true);

        }
        return total;
    }


}


