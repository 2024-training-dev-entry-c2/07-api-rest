package com.restaurant.restaurant.utils;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.MenuModel;
import com.restaurant.restaurant.models.OrderModel;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
  public static ClientDTO mapToClientDTO(ClientModel client){
    ClientDTO dto = new ClientDTO();
    dto.setId(client.getId());
    dto.setName(client.getName());
    dto.setLastName(client.getLastName());
    dto.setEmail(client.getEmail());
    dto.setPhone(client.getPhone());
    dto.setIsFrecuent(client.getIsFrecuent());
    return dto;
  }

  public static ClientModel mapToClientModel(ClientDTO client){
    ClientModel model = new ClientModel();
    model.setName(client.getName());
    model.setLastName(client.getLastName());
    model.setEmail(client.getEmail());
    model.setPhone(client.getPhone());
    model.setIsFrecuent(client.getIsFrecuent());
    return model;
  }

  public static DishDTO mapToDishDTO(DishModel dish){
    DishDTO dto = new DishDTO();
    dto.setId(dish.getId());
    dto.setName(dish.getName());
    dto.setPrice(dish.getPrice());
    dto.setIsPopular(dish.getIsPopular());
    return dto;
  }

  public static DishModel mapToDishModel(DishDTO dish){
    DishModel model = new DishModel();
    model.setName(dish.getName());
    model.setPrice(dish.getPrice());
    model.setIsPopular(dish.getIsPopular());
    return model;
  }

  public static MenuDTO mapToMenuDTO(MenuModel menu){
    List<Long> dishIds = menu.getDishes().stream().map(DishModel::getId).collect(Collectors.toList());
    MenuDTO dto = new MenuDTO();
    dto.setId(menu.getId());
    dto.setName(menu.getName());
    dto.setDescription(menu.getDescription());
    dto.setDishIds(dishIds);
    return dto;
  }

  public static MenuModel mapToMenuModel(MenuDTO menu){
    MenuModel model = new MenuModel();
    model.setId(menu.getId());
    model.setName(menu.getName());
    model.setDescription(menu.getDescription());
    return model;
  }

  public static OrderDTO mapToOrderDTO(OrderModel order){
    List<Long> dishIds = order.getDishes().stream().map(DishModel::getId).collect(Collectors.toList());
    OrderDTO dto = new OrderDTO();
    dto.setId(order.getId());
    dto.setClientId(order.getClient().getId());
    dto.setDishIds(dishIds);
    dto.setDate(order.getDate());
    return dto;
  }

  public static OrderModel mapToOrderModel(OrderDTO order){
    OrderModel model = new OrderModel();
    model.setId(order.getId());
    return model;
  }
}
