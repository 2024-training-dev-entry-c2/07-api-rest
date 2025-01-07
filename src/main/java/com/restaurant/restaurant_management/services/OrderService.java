package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import com.restaurant.restaurant_management.repositories.OrderRepository;
import com.restaurant.restaurant_management.services.ChainOfResponsibility.FrequentClientDiscountHandler;
import com.restaurant.restaurant_management.services.ChainOfResponsibility.PriceHandler;
import com.restaurant.restaurant_management.services.ChainOfResponsibility.SumOrderDetailsHandler;
import com.restaurant.restaurant_management.services.observer.EventManager;
import com.restaurant.restaurant_management.services.observer.FrequentClientObserver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private PriceHandler priceHandlerChain;
  private final EventManager eventManager =  new EventManager("NewOrder", "DishOrdered");

  public OrderService(OrderRepository orderRepository, ClientRepository clientRepository) {
    this.orderRepository = orderRepository;
    eventManager.subscribe("NewOrder", new FrequentClientObserver(clientRepository));
    //Se configura la cadena de responsabilidad
    PriceHandler sumDetailsHandler = new SumOrderDetailsHandler();
    PriceHandler frequentClientHandler = new FrequentClientDiscountHandler();
    sumDetailsHandler.setNextHandler(frequentClientHandler);
    this.priceHandlerChain = sumDetailsHandler;
  }

  public ClientOrder saveOrder(ClientOrder order) {
    order.setOrderDateTime(LocalDateTime.now());
    order.setDiscount(0.0);
    order.setTotal(0.0);
    ClientOrder newOrder = orderRepository.saveAndFlush(order);
    eventManager.notify("NewOrder", newOrder.getClient());
    return newOrder;
  }

  public Optional<ClientOrder> getOrder(Long id) {
    return orderRepository.findById(id);
  }

  public List<ClientOrder> findOrdersByDate(LocalDate specificDate) {
    LocalDateTime startDate = specificDate.atStartOfDay();
    LocalDateTime endDate = specificDate.atTime(LocalTime.MAX);

    return orderRepository.findByOrderDateTimeBetween(startDate, endDate);
  }

  public ClientOrder updateOrder(Long id, ClientOrder order) {
    return orderRepository.findById(id).map(x -> {
      x.setStatus(order.getStatus());
      x.setClient(order.getClient());
      return orderRepository.save(x);
    }).orElseThrow(() -> new RuntimeException("Pedido con el id " + id + " no pudo ser actualizado"));
  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }

  public void updateTotalOrder(Long orderId) {
    orderRepository.findById(orderId).ifPresent(x -> {
      x.setTotal(priceHandlerChain.calculateTotal(x, 0.0));
      orderRepository.save(x);
    });
  }
}
