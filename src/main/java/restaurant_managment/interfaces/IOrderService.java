package restaurant_managment.interfaces;

import restaurant_managment.Models.OrderModel;

import java.util.Optional;

public interface IOrderService {
  Optional<OrderModel> getOrderById(Long id);

}