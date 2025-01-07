package com.example.restaurant.services.order;

import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
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


  public OrderResponseDTO createOrder(com.example.restaurant.models.dto.order.OrderRequestDTO orderDTO) {
    return createOrderCommand.execute(orderDTO);
  }

  public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderDTO) {
    return updateOrderCommand.execute(id, orderDTO);
  }

  public void deleteOrder(Long orderId) {
    deleteOrderCommand.execute(orderId);
  }

  public OrderResponseDTO getOrderById(Long orderId) {
    return getOrderByIdCommand.execute(orderId);
  }

  public List<OrderResponseDTO> listOrders() {
    return listOrdersCommand.execute();
  }
}
