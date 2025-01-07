package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.repositories.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;

  public OrderDetailService(OrderDetailRepository orderDetailRepository) {
    this.orderDetailRepository = orderDetailRepository;
  }

  public void saveOrderDetail(OrderDetail orderDetail) {
    orderDetailRepository.save(orderDetail);
  }

  public Optional<OrderDetail> getOrderDetail(Long id) {
    return orderDetailRepository.findById(id);
  }

  public void saveOrderDetails(List<OrderDetail> orderDetails) {
    orderDetailRepository.saveAll(orderDetails);
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
