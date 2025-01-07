package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public ClientOrder saveOrder(ClientOrder order) {
    order.setOrderDateTime(LocalDateTime.now());
    order.setDiscount(0.0);
    order.setTotal(0.0);
    return orderRepository.save(order);
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
}
