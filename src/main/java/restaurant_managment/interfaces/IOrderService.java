package restaurant_managment.interfaces;

import restaurant_managment.Models.OrderModel;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
  List<OrderModel> getAllOrders();
  Optional<OrderModel> getOrderById(Long id);

}