package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.DetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderDetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderDetailResponseDTO;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.OrderDetail;

public class DtoOrderDetailConverter {

  public static OrderDetail convertToOrderDetail(OrderDetailRequestDTO orderDetailRequestDTO, ClientOrder clientOrder, Dish dish) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setQuantity(orderDetailRequestDTO.getQuantity());
    orderDetail.setClientOrder(clientOrder);
    orderDetail.setDish(dish);
    return orderDetail;
  }

  public static OrderDetail convertToOrderDetail(DetailRequestDTO detailRequestDTO, ClientOrder clientOrder, Dish dish) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setQuantity(detailRequestDTO.getQuantity());
    orderDetail.setClientOrder(clientOrder);
    orderDetail.setDish(dish);
    return orderDetail;
  }

  public static OrderDetailResponseDTO convertToResponseDTO(OrderDetail orderDetail) {
    OrderDetailResponseDTO orderDetailResponseDTO = new OrderDetailResponseDTO();
    orderDetailResponseDTO.setId(orderDetail.getId());
    orderDetailResponseDTO.setQuantity(orderDetail.getQuantity());
    orderDetailResponseDTO.setSubtotal(orderDetail.getSubtotal());
    orderDetailResponseDTO.setOrderId(orderDetail.getClientOrder().getId());
    orderDetailResponseDTO.setDish(orderDetail.getDish());
    return orderDetailResponseDTO;
  }

}
