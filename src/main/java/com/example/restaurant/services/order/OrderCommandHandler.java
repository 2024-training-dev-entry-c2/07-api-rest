package com.example.restaurant.services.order;

import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.services.order.commands.CreateOrderCommand;
import com.example.restaurant.services.order.commands.DeleteOrderCommand;
import com.example.restaurant.services.order.commands.GetOrderByIdCommand;
import com.example.restaurant.services.order.commands.ListOrdersCommand;
import com.example.restaurant.services.order.commands.UpdateOrderCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderCommandHandler {

  private final CreateOrderCommand createOrderCommand;
  private final UpdateOrderCommand updateOrderCommand;
  private final DeleteOrderCommand deleteOrderCommand;
  private final GetOrderByIdCommand getOrderByIdCommand;
  private final ListOrdersCommand listOrdersCommand;


  public OrderDTO createCustomer(OrderDTO orderDTO) {
    return createOrderCommand.execute(orderDTO);
  }

  public OrderDTO updateCustomer(OrderDTO orderDTO) {
    return updateOrderCommand.execute(orderDTO.getId(), orderDTO);
  }

  public void deleteCustomer(Long orderId) {
    deleteOrderCommand.execute(orderId);
  }

  public OrderDTO getCustomerById(Long orderId) {
    return getOrderByIdCommand.execute(orderId);
  }

  public List<OrderDTO> listCustomers() {
    return listOrdersCommand.execute();
  }
}
