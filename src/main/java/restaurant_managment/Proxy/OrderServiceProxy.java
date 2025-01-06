package restaurant_managment.Proxy;

import restaurant_managment.Models.OrderModel;
import restaurant_managment.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderServiceProxy implements IOrderService {

  @Autowired
  private IOrderService orderService;

  private Map<Long, OrderModel> orderCache = new HashMap<>();

  @Override
  public Optional<OrderModel> getOrderById(Long id) {
    if (!orderCache.containsKey(id)) {
      Optional<OrderModel> order = orderService.getOrderById(id);
      order.ifPresent(value -> orderCache.put(id, value));
    }
    return Optional.ofNullable(orderCache.get(id));
  }
}