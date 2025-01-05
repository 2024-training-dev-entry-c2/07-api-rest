package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant_managment.Models.OrderModel;
import restaurant_managment.Repositories.OrderRepository;
import restaurant_managment.interfaces.IOrderService;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<OrderModel> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Optional<OrderModel> getOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public OrderModel createOrder(OrderModel order) {

    return orderRepository.save(order);
  }

  public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
    return orderRepository.findById(id)
      .map(order -> {
        order.setReservation(updatedOrder.getReservation());
        order.setDishes(updatedOrder.getDishes());
        order.setStatus(updatedOrder.getStatus());
        return orderRepository.save(order);
      })
      .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }
}