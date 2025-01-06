package com.example.restaurant.services.order;

import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.services.order.commands.CreateOrderCommand;
import com.example.restaurant.services.order.commands.DeleteOrderCommand;
import com.example.restaurant.services.order.commands.GetOrderByIdCommand;
import com.example.restaurant.services.order.commands.ListOrdersCommand;
import com.example.restaurant.services.order.commands.UpdateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCommandHandler {

  private final CreateOrderCommand createOrderCommand;
  private final UpdateOrderCommand updateOrderCommand;
  private final DeleteOrderCommand deleteOrderCommand;
  private final GetOrderByIdCommand getOrderByIdCommand;
  private final ListOrdersCommand listOrdersCommand;


  public OrderDTO createOrder(OrderDTO orderDTO) {
    return createOrderCommand.execute(orderDTO);
  }

  public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
    return updateOrderCommand.execute(id, orderDTO);
  }

  public void deleteOrder(Long orderId) {
    deleteOrderCommand.execute(orderId);
  }

  public OrderDTO getOrderById(Long orderId) {
    return getOrderByIdCommand.execute(orderId);
  }

  public List<OrderDTO> listOrders() {
    return listOrdersCommand.execute();
  }
}
