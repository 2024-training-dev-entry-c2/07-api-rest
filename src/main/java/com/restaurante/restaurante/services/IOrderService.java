package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.OrderDTO;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    OrderDTO addOrder(OrderDTO orderDTO);
    Optional<OrderDTO> getOrder(Long id);
    List<OrderDTO> getOrders();
    void deleteOrder(Long id);
    OrderDTO updateOrder(Long id, OrderDTO orderDTO);
}