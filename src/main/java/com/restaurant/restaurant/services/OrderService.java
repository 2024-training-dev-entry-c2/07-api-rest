package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.CreateOrderDTO;
import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.exceptions.BusinessException;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.enums.ClientType;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.enums.DishType;
import com.restaurant.restaurant.models.OrderModel;
import com.restaurant.restaurant.repositories.IClientRepository;
import com.restaurant.restaurant.repositories.IDishRepository;
import com.restaurant.restaurant.repositories.IOrderRepository;
import com.restaurant.restaurant.utils.UtilCost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final IOrderRepository IOrderRepository;
  private final IClientRepository IClientRepository;
  private final IDishRepository IDishRepository;


  @Transactional
  public List<OrderDTO> findAll(){
    return IOrderRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public OrderDTO findById(Long id){
    return IOrderRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
  }

  @Transactional
  public List<OrderDTO> findByClientId(Long clientId){
    if(!IClientRepository.existsById(clientId)){
      throw new ResourceNotFoundException("Client not found with id " + clientId);
    }
    return IOrderRepository.findByClientId(clientId).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public OrderDTO createOrder(CreateOrderDTO createOrderDTO){
    ClientModel clientModel = IClientRepository.findById(createOrderDTO.getClientId()).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    List<DishModel> dishModels = createOrderDTO.getDishIds().stream().map(id -> IDishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dish not found " + id))).collect(Collectors.toList());

    if(dishModels.isEmpty()){
      throw new BusinessException("Dish list cannot be empty");
    }

    OrderModel orderModel = new OrderModel();
    orderModel.setClient(clientModel);
    orderModel.setDishes(dishModels);
    orderModel.setDate(LocalDateTime.now());

    Double totalCost = calculateTotalCost(dishModels, clientModel);
    orderModel.setTotalCost(totalCost);
    OrderModel savedOrder = IOrderRepository.save(orderModel);
    updateStateClient(clientModel);
    dishModels.forEach(this::updateStateDish);
    return convertToDto(savedOrder);
  }

  @Transactional
  public OrderDTO updateOrder(Long id, OrderDTO orderDTO){
    OrderModel orderModel = IOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));

    List<DishModel> dishModels = orderDTO.getDishIds().stream().map(dishId -> IDishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + dishId))).collect(Collectors.toList());

    if(dishModels.isEmpty()){
      throw new BusinessException("Dish list cannot be empty");
    }

    orderModel.setDishes(dishModels);
    Double totalCost = calculateTotalCost(dishModels, orderModel.getClient());
    orderModel.setTotalCost(totalCost);

    return convertToDto(IOrderRepository.save(orderModel));
  }

  @Transactional
  public void deleteOrder(Long id){
    if(!IOrderRepository.existsById(id)){
      throw new ResourceNotFoundException("Order not found with id " + id);
    }
    IOrderRepository.deleteById(id);
  }

  private Double calculateTotalCost(List<DishModel> dishModels, ClientModel clientModel){
    Double totalCost = dishModels.stream().mapToDouble(DishModel::getPrice).sum();
    if(clientModel.getType().equals(ClientType.FRECUENT)){
      totalCost = UtilCost.applyDescClientFrecuent(totalCost);
    }
    return totalCost;
  }

  protected void updateStateClient(ClientModel clientModel){
    Long countOrders = Long.valueOf(IOrderRepository.countOrdersByClientId(clientModel.getId()));
    if(countOrders >= 10 && clientModel.getType().equals(ClientType.COMUN)){
      clientModel.setType(ClientType.FRECUENT);
      IClientRepository.save(clientModel);
    }
  }

  private void updateStateDish(DishModel dishModel){
    Long salesCount = Long.valueOf(IOrderRepository.countOrdersByDishId(dishModel.getId()));
    if(salesCount >= 100 && dishModel.getType().equals(DishType.COMUN)){
      dishModel.setType(DishType.POPULAR);
      dishModel.setPrice(UtilCost.applyIncrDishPopular(dishModel.getPrice()));
      IDishRepository.save(dishModel);
    }
  }

  private OrderDTO convertToDto(OrderModel orderModel) {
    OrderDTO dto = new OrderDTO();
    dto.setId(orderModel.getId());
    dto.setClientId(orderModel.getClient().getId());
    dto.setDishIds(orderModel.getDishes().stream().map(DishModel::getId).collect(Collectors.toList()));
    dto.setDate(orderModel.getDate());
    dto.setTotalCost(orderModel.getTotalCost());
    return dto;
  }
}
