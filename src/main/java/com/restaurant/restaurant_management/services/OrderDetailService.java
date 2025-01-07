package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.repositories.DishRepository;
import com.restaurant.restaurant_management.repositories.OrderDetailRepository;
import com.restaurant.restaurant_management.services.observer.EventManager;
import com.restaurant.restaurant_management.services.observer.PopularDishObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;
  private final EventManager eventManager =  new EventManager("NewOrder", "DishOrdered");

  public OrderDetailService(OrderDetailRepository orderDetailRepository, DishRepository dishRepository) {
    this.orderDetailRepository = orderDetailRepository;
    eventManager.subscribe("DishOrdered", new PopularDishObserver(dishRepository, orderDetailRepository));
  }

  public void saveOrderDetail(OrderDetail orderDetail) {
    orderDetailRepository.save(orderDetail);
  }

  public Optional<OrderDetail> getOrderDetail(Long id) {
    return orderDetailRepository.findById(id);
  }

  public List<OrderDetail> saveOrderDetails(List<OrderDetail> orderDetails) {
    List<OrderDetail> newDetails = orderDetailRepository.saveAllAndFlush(orderDetails);
    for (OrderDetail detail : orderDetails) {
      eventManager.notify("DishOrdered", detail.getDish());
    }
    return newDetails;
  }

  public List<OrderDetail> listOrderDetailsByOrderId(Long orderId) {
    return orderDetailRepository.findByOrderId(orderId);
  }

  public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
    return orderDetailRepository.findById(id).map(x -> {
      x.setQuantity(orderDetail.getQuantity());
      x.setDish(orderDetail.getDish());
      x.calculateSubtotal();
      return orderDetailRepository.save(x);
    }).orElseThrow(() -> new RuntimeException("Detalle de pedido con el id " + id + " no pudo ser actualizado"));
  }

  public void deleteOrderDetail(Long id) {
    orderDetailRepository.deleteById(id);
  }

}
